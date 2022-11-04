# Rules-Engine

This repository contains the Kotlin and TypeScript implementaions of Rules-Engine

To learn more about the individual implementations:
- Take a peek at the implementation and documentation
- Refer to a conference presentation introducing the implementations:
[warnermedia.github.io/all-things-open-22](https://warnermedia.github.io/all-things-open-22)

## Using the implementations

The Rules Engine implementations are published to GitHub Packages.
Therefore, an additional step is required to allow any project to be able to 
download the Kotlin/TypeScript implementations locally

Setup [GitHub Packages](https://docs.github.com/en/packages/learn-github-packages/introduction-to-github-packages#authenticating-to-github-packages) using the using the
attached link

Once the PAT (personal access token) is generated with `packages:read`,
update the `.zshrc`, `.bashrc`, or the default shell with the following:

```bash
export GITHUB_ACTOR="github-username"
export GITHUB_TOKEN="generated-personal-access-token"
```

The following will allow installing packages from GitHub Package registry

Note: `GITHUB_ACTOR` and `GITHUB_TOKEN` are already present in GitHub Actions. The
above steps are only required to install the packages locally but not during a job
running on GitHub Actions

You can pass in the token during CI like the following:

```yml
      - run: npm install
        env:
            GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

For more information about using GitHub Packages registry, go
[here](https://docs.github.com/en/packages/working-with-a-github-packages-registry)
