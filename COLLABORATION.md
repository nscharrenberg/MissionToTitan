# Collaboration Guidelines

The collaboration guidelines are made as to make the collaboration between all the developers as smooth as possible.



## Project Board

The project board as located in https://github.com/nscharrenberg/MissionToTitan/projects contains the all the tasks that have to be done.



### Task Workflow

If you want to work on a task then you need to follow a few steps:

1. Drag the task you are going to work on in the project board from "To do" to "In progress".
2. Remove all assignees of that task except those that are actually working on it.
   1. (Optional) Give a better description of the task.
   2. (Optional) If from the description, you notice it's a big task that could better be split into multiple smaller tasks, then follow the steps from "Split a task into smaller tasks".
   3. (Optional) Attach the correct milestone if it has not yet been attached.
3. Create a new Branch from `master` (or another branch) that uses the following naming convention: "{issue_id}-{issue_title}", where `{issue_id}` is the id of the issue. (Can be found next to the title of the issue/task prefixed with a `#`), and the `{issue_title}` is the title of the task/issue.
   e.g. `31-Write Collaboration File`.
4. When your task is done and the code is ready to be merged back into the `master` branch, then you need to create a Pull Request. Ensure the `Base` is the `main` (or another parent branch) and the `Compare` is your `issue-branch`. 
5. Then give a good & descriptive title for the pull request where you end with "close #{issue_id}" or "fix #{issue_id}" or "resolve #{issue_id}" e.g. `Adds a Collaboration Guidelines file with instruction - close #31`. (the syntaxes like `close #31` will auto-link the pull request to the issue, and move the task in the project board to "Review In Progress").
6. Also assign somebody as a Reviewer to the Pull Request (e.g. `nscharrenberg`).
7. press the `Create Pull Request` button.

The pull request should now be linked to the issue causing the task (which is auto connected to the issue) to be moved to "Review in Progress". 

Note: If you have not yet linked the Pull Request or forgot, you can do that inside the issue as well by clicking `Linked pull requests` and selecting the pull request.

Note: If you completely forgot to link the PR and it has already been merged, then you can manually close the issue and check the project board to ensure it has been moved to "Done".



### Split a task into Smaller tasks

During the start of a new phase these tasks may be rigorous or roughly described, and may need to be divided into smaller tasks.

Below are some instructions on how you could create a new task:

1. Click the `+` on the "To do" lane in the Project board.
2. Give a short description about the new task (and c lick `Add`)
3. Click on the `...` (three dots) of that task and click `Convert to Issue`.
4. Click on the task and assign the people working on the task, give it an appropriate label (only existing labels) and give it a milestone (if applicable).
5. If the task falls under another task then makes sure to cross-reference the other task in the description of the current task, by adding `#{issue_id}` to the description. e.g. issue #31  falls under issue #30 then you need to have `#30` in the description of issue #31. (This will notify issue #31 that it's being crosslinked, and thus has connected issues.)

Note: If an issue has "child issues" under itself, then it would be good practice to make a checklist with all it's child issues as well, so you have a good overview to what issues it depends on.



## Code Review

When you are attached to an issue for a code review, your task is going to be fairly straight forward: Evaluate the written code and point out any feedback you have. 
This can be things like:

- Broken code
- Inefficient Code
- Unused imports
- Unused variables, methods, libraries etc...
- Bugs, exploits
- weird variable, method, class naming
- undocumented and unclear code
- Any other kind of flaws

If anything non-breaking feedback is given, then add comments to those lines and message the person that is responsible for that task fix it. (Don't accept or decline).

If you give feedback because you think it's breaking anything or it's fundamentally flawed, then comment on them, decline the PR and message the person that is responsible for that task to fix and extensively test it. (this is to prevent accidentally merging broken code)


Once everything is fine, accept the pull request and make sure the task is send to "Done".



### Feedback has been given what should I do?

If minor feedback (non-breaking) has been given and the pull request has remained open, then you can simply commit to the branch and the pull request will automatically update with your latest changes. Once you're done just message the reviewer again and he'll handle it again.

If major feedback (breaking) has been given, then you can also just continue on the same branch but when you are done you need to go through the Pull Request process again, and assign a reviewer again and message him to check it out.

