from git import Repo #Con GitPython se construye directament del historial de Git sin hardcodear nada
from collections import defaultdict
import json
import os
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

#Objetivo: generar un vector similar al de los usuarios, pero basado en los ficheros tocados en la PR

# Inicializar repo
repo = Repo(".")
user_contexts = defaultdict(lambda: defaultdict(int)) #Vector de contexto por usuario con su experiencia tecnica
author_map = {} #Mapeo de nombre real(github login)

#Parser del repo que identifica clases tocadas por cada commit(por autor)
for commit in repo.iter_commits("HEAD", max_count=1000): #Lee hasta 1000 comits
    author_name = commit.author.name
    author_email = commit.author.email

    # Inferir el GitHub login si está disponible en los datos (opcional)
    github_login = author_name.lower().replace(" ", "")  # Personaliza si quieres más precisión

    author_map[author_name] = github_login

    for file_path in commit.stats.files: #Por cada archivo modificado extrae,nombre de archivo,ruta(paquete), extensión(tipo)
        parts = file_path.split("/")
        if len(parts) < 2:
            continue

        package = ".".join(parts[:-1])
        filename = parts[-1]
        ext = filename.split(".")[-1]

        #Guardar vectores de contexto por usuario
        #Se parsea por cada usuario, que clases ha tocado, que paquetes ha tocado(por el path del archivo) y que tipo de archivo es
        user_contexts[github_login][f"file:{filename}"] += 1
        user_contexts[github_login][f"package:{package}"] += 1
        user_contexts[github_login][f"type:{ext}"] += 1

# Guardar vectores contextuales
with open("user_contexts.json", "w") as f:
    json.dump({u: dict(v) for u, v in user_contexts.items()}, f, indent=2) #Convert to final matrix

# Guardar mapeo de autores
with open("author_map.json", "w") as f:
    json.dump(author_map, f, indent=2)
    
df = pd.DataFrame.from_dict(user_contexts, orient="index").fillna(0)

top_contexts = df.sum(axis=0).sort_values(ascending=False).head(30).index
df_filtered = df[top_contexts]

plt.figure(figsize=(max(10, len(top_contexts) * 0.5), 8))  # auto-ajuste tamaño

sns.heatmap(df_filtered, annot=True, fmt=".0f", cmap="YlGnBu", cbar=True)

plt.title("Mapa de conocimiento técnico por usuario", fontsize=14)
plt.xlabel("Contexto", fontsize=12)
plt.ylabel("Usuario", fontsize=12)

plt.xticks(rotation=45, ha="right")
plt.yticks(rotation=0)

plt.tight_layout()
plt.savefig("knowledge_heatmap.png", dpi=300) #Heatmap con seaborn del conocimiento por usuario

print("✅ user_contexts.json y author_map.json generados correctamente.")
