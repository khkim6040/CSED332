# Team `4nix`

## Members

Jinyoung Kim, Chanho Song, Jinmin Goh, Hyunbin Park, Seokhwan Choi, Gwanho Kim

## Experiences

- **Jinyoung Kim**
  - Java, IntelliJ IDEA
  - C, Python, Verilog
  - C#, Unity
- **Chanho Song**
  - Java, IntelliJ IDEA
  - C/C++, Python
- **Jinmin Goh**
  - Java, IntelliJ IDEA
  - C/C++, Python
- **Hyunbin Park**
  - Java, IntelliJ IDEA
  - C/C++, Python, Verilog, SQL
- **Seokhwan Choi**
  - Java, IntelliJ IDEA
  - C/C++
  - C#, Unity
- **Gwanho Kim**
  - Java, IntelliJ IDEA
  - C/C++, Python
  - FastAPI, Alembic, SQLAlchemy, Pydantic

# Role Distribution & Pairs Across Iterations

| Role | <span dir="">\~</span> Iteration 2 (Period 1) | Iteration 2 <span dir="">\~</span> (Period 2) |
|------|-----------------------------------------------|-----------------------------------------------|
| Team/Dev Leader | Chanho Song | Gwanho Kim |
| QA Leader | Gwanho Kim | Jinmin Goh |
| Planning Leader | Jinmin Goh | Hyunbin Park |
| Presenter | Jinyoung Kim | Seokhwan Choi |
| Developers | Seokhwan Choi, Hyunbin Park | Chanho Song, Jinyoung Kim |

<table>
<tr>
<th rowspan="4">Pairs</th>
<th>

<span dir="">\~</span> Iteration 2 (Period 1)
</th>
<th>

Iteration 2 <span dir="">\~</span> (Period 2)
</th>
</tr>
<tr>
<td>Chanho Song, Hyunbin Park</td>
<td>Chanho Song, Jinyoung Kim</td>
</tr>
<tr>
<td>Jinmin Goh, Seokhwan Choi</td>
<td>Jinmin Goh, Gwanho Kim</td>
</tr>
<tr>
<td>Gwanho Kim, Jinyoung Kim</td>
<td>Hyunbin Park, Seokwahn Choi</td>
</tr>
</table>

## Project Description

**Project Title: Code Scent, IntelliJ IDEA Plugin for Code Smell Detection**

**What is the problem that your team wants to address?**

- In software development, it's crucial to keep the code clean, easy to read, and well-organized so that the project can grow and last a long time. However, developers sometimes add **"code smells"** without realizing it.
- **"Code smells"** are suboptimal coding patterns that don't prevent the code from functioning, but they make the code harder to understand and improve. These can cause problems when fixing bugs, adding new features, or making any changes to the code. Also, they might lead to new bugs.
- If software development tools aren't great at spotting these issues, developers will have to check their code manually, which can take a long time. To solve this problem, we decided to develop **"Code Scent"** for our project.

**Why is your project interesting? Is it indeed helpful for software development?**

- Our project, **"Code Scent"**, focuses on improving code quality by detecting **code smells** within the developer's workspace.
- As code quality is a critical component of software development that directly influences performance, maintainability, and scalability, our tool will identify code smells as soon as they appear to prevent the buildup of technical debt.
- Our project is indeed helpful for software development as maintaining code quality is particularly valuable in today's fast-paced software development environments, which rely on agile and rapid iteration.
- **A unique feature of our plugin is "customization".** The user can customize the precondition for certain code smells, so that the code smell condition fits to user's convenience. The user can customize preconditions of "large class due to fields", "large class due to methods", "long method", "message chain", "long parameter list", and "comments" code smells.

**How can your proposed project deal with the problem? can you write a description for me?**

- **"Code Scent"** will be our smart assistant for coding that is compatible with IntelliJ IDEA. It checks your code when you run the plugin, catching any **code smells** that occur in the code files.
- Our project detects common code smells such as long methods, large classes, duplicated code etc. Addressing these will enable users to maintain clean and well-organized code in their projects.
- When the user runs the plugin by clicking "Code Scent" tab, "Analyze", "Analyze All", and "Customization" tabs appear in the bottom left corner. Clicking the 'Analyze' button triggers the plugin to scan for code smells in the current opened file. The plugin then displays the results line by line in the bottom window as a tree structure. Clicking on a message highlights the corresponding lines of code while double-clicking shifts the user's screen focus directly to the related code segment.

## Weekly Meetings

- Scheduled meetings are held on every Monday, 8 pm
- Hold additional meetings if needed
