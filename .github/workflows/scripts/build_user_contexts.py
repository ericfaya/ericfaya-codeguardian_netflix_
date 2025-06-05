from git import Repo
from collections import defaultdict
import json
import os

repo = Repo(".")
user_contexts = defaultdict(lambda: defaultdict(int))

default_branch = repo.remotes.origin.refs['HEAD'].reference.remote_head
for commit in repo.iter_commits(default_branch, max_count=1000):
    author = commit.author.name
    for file in commit.stats.files:
        parts = file.split("/")
        if len(parts) < 2:
            continue
        package = ".".join(parts[:-1])
        filename = parts[-1]
        ext = filename.split(".")[-1]

        user_contexts[author][f"file:{filename}"] += 1
        user_contexts[author][f"package:{package}"] += 1
        user_contexts[author][f"type:{ext}"] += 1

# Guardar los vectores contextuales
with open("user_contexts.json", "w") as f:
    json.dump({u: dict(v) for u, v in user_contexts.items()}, f, indent=2)
