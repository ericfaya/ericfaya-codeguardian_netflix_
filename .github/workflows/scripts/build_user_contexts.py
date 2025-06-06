from git import Repo
from collections import defaultdict
import json
import os

# Inicializar repo
repo = Repo(".")
user_contexts = defaultdict(lambda: defaultdict(int))
author_map = {}

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

        user_contexts[github_login][f"file:{filename}"] += 1
        user_contexts[github_login][f"package:{package}"] += 1
        user_contexts[github_login][f"type:{ext}"] += 1

# Guardar vectores contextuales
with open("user_contexts.json", "w") as f:
    json.dump({u: dict(v) for u, v in user_contexts.items()}, f, indent=2)

# Guardar mapeo de autores
with open("author_map.json", "w") as f:
    json.dump(author_map, f, indent=2)

print("✅ user_contexts.json y author_map.json generados correctamente.")
