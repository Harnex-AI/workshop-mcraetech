# Workshop 1 — Cursor AI-Accelerated Development
Modules: Advanced Prompting (M1), Context Engineering (M3), Model Selection (M4), Docs Automation (M7), McCraeTech AI Standard.

## Prerequisites
- **Java Track**: JDK 17+, Maven 3.6+
- **C# Track**: .NET 8.0 SDK
- **Cursor IDE**: Latest version with Docs panel enabled

## Setup
1. Open this repository in Cursor IDE
2. Add `/context` files to Cursor's **Docs** panel (right sidebar)
3. For Java example: `cd examples/auckland-java-consent && mvn clean install`
4. For .NET example: `cd examples/thailand-dotnet-legacy && dotnet restore`

## Use in the Session
Follow `workshop/*` in order. Only assisted workflows here; spec-driven/agentic are Workshop 2.

## Quick Reference
- **Cursor Rules**: `.cursor/rules/*.mdc` (auto-applied based on file globs)
- **Prompt Patterns**: `prompts/*.pattern.md` (copy/paste and fill placeholders)
- **Context Docs**: `context/*.md` (add to Cursor Docs for grounding)
- **Model Routing**: `ops/model-routing.json` (small/medium/large selection policy)

