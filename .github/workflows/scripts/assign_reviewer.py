import os
import json
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
    score = sum(min(vec1[k], vec2[k]) for k in common_keys)
    return score

# Evaluar PRs abiertas
for pr in repo.get_pulls(state="open"):
    if pr.commits < 5: # or pr.requested_reviewers:
        continue

    print(f"Evaluando PR #{pr.number} con {pr.commits} commits...")

    author = pr.user.login.lower()

    files = pr.get_files()
    pr_vector = vector_from_files(files)

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
