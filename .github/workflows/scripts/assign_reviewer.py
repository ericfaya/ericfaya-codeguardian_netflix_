import os
import json
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

from github import Github
from collections import defaultdict

# Cargar contexto histórico
with open("user_contexts.json") as f:
    user_contexts = json.load(f)

# Conectar con GitHub
token = os.environ["TOKEN_GITHUB"]
repo_name = os.environ["GITHUB_REPOSITORY"]
gh = Github(token)
repo = gh.get_repo(repo_name)

#Se genera un vector similar al de los usuarios, pero basado en los archivos tocados en la PR
def vector_from_files(file_paths):
    vector = defaultdict(int)
    for file in file_paths:
        parts = file.filename.split("/")
        if len(parts) < 2:
            continue
        package = ".".join(parts[:-1])
        filename = parts[-1]
        ext = filename.split(".")[-1]
        vector[f"file:{filename}"] += 1
        vector[f"package:{package}"] += 1
        vector[f"type:{ext}"] += 1
    return dict(vector)

def similarity(vec1, vec2):
    common_keys = set(vec1.keys()) & set(vec2.keys())
    if not common_keys:
        return 0
        #PROBLEMA:si los usuarios tienen presencia en los mismos contextos clave, pero en diferente magnitud, el score apenas cambia
    score = sum(min(vec1[k], vec2[k]) for k in common_keys) #EJ min(22,1) + min (461,21) + .... = score
    #Si el PR tiene type:java = 1 y el user tiene type:java = 500, entonces min(1,500)=1 solo cuenta como 1 punto de afinidad
    return score

# Evaluar PRs abiertas
pr_contexts = {}
for pr in repo.get_pulls(state="open"):
    if pr.commits < 5: # or pr.requested_reviewers:
        continue

    print(f"Evaluando PR #{pr.number} con {pr.commits} commits...")

    author = pr.user.login.lower()

    files = pr.get_files()
    pr_vector = vector_from_files(files)
    pr_contexts[f"PR#{pr.number}"] = pr_vector
    
    with open("pr_contexts.json", "w") as f:
        json.dump(pr_contexts, f, indent=2)
    
    best_user = None
    best_score = -1

    # Calcular mejor afinidad
    for user, context in user_contexts.items():
        score = similarity(pr_vector, context)
        print(f"  Afinidad con {user}: {score}")
        if score > best_score:
            best_score = score
            best_user = user

    # Si el mejor es el autor, buscar siguiente mejor
    if best_user and best_user.lower() == author:
        print(f"⚠️ {best_user} es el autor de la PR. Buscando siguiente mejor revisor...")
        second_best_user = None
        second_best_score = -1

        for user, context in user_contexts.items():
            if user.lower() == author:
                continue
            score = similarity(pr_vector, context)
            if score > second_best_score:
                second_best_score = score
                second_best_user = user

        best_user = second_best_user
        best_score = second_best_score

    if best_user:
        try:
            pr.create_review_request([best_user])
            print(f"✅ Asignado revisor automático: {best_user} con afinidad {best_score}")
        except Exception as e:
            print(f"⚠️ Error al asignar a {best_user}: {e}")
    else:
        print("❌ No se encontró ningún revisor válido.")

# Cargar contexto de PRs
with open("pr_contexts.json") as f:
    pr_contexts = json.load(f)

# Convertir a DataFrame
df = pd.DataFrame.from_dict(pr_contexts, orient="index").fillna(0)

# (Opcional) Mostrar solo los contextos más relevantes
top_contexts = df.sum(axis=0).sort_values(ascending=False).head(30).index
df_filtered = df[top_contexts]

# Dibujar heatmap
plt.figure(figsize=(max(10, len(top_contexts) * 0.5), 8))
sns.heatmap(df_filtered, annot=True, fmt=".0f", cmap="YlGnBu", cbar=True)

plt.title("Vector de contexto por PR", fontsize=14)
plt.xlabel("Contexto", fontsize=12)
plt.ylabel("Pull Request", fontsize=12)

plt.xticks(rotation=45, ha="right")
plt.yticks(rotation=0)

plt.tight_layout()
plt.savefig("pr_heatmap.png", dpi=300)
