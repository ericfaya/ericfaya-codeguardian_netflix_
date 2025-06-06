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
user_contexts = defaultdict(lambda: defaultdict(int)) #Vector de contexto por usuario
author_map = {}

#Parser del repo que identifica clases tocadas por cada commit(por autor)
for commit in repo.iter_commits("HEAD", max_count=1000):
    author_name = commit.author.name
    author_email = commit.author.email

    # Inferir el GitHub login si está disponible en los datos (opcional)
    github_login = author_name.lower().replace(" ", "")  # Personaliza si quieres más precisión

    author_map[author_name] = github_login

    for file_path in commit.stats.files:
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
plt.figure(figsize=(10, 6))
sns.heatmap(df, annot=True, cmap="YlGnBu")
plt.title("Mapa de conocimiento técnico por usuario")
plt.xlabel("Contexto")
plt.ylabel("Usuario")
plt.tight_layout()
plt.show()

print("✅ user_contexts.json y author_map.json generados correctamente.")
