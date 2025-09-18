Create a new repo from UI -> add name -> follow the commands displayed

echo "# DSA" >> README.md
git init
git config --global init.defaultBranch main (for keeping branh names uniform across repos)

git add README.md
git commit -m “project setup”
git branch -M main
git remote add origin https://github.com/JoyPGit/ecommerceDocker.git [_your repo name_]
git push -u origin main


-- **rebase**
1 Edit old commit message
git rebase -i HEAD~n -> i (interactive)
Now replace [*pick*] with [*reword*] for the commits you want to edit the messages of:
(Don’t update the message now, will be done in the next step)
(esc):wq!
-> next screen ->  i (interactive) -> update message -> (esc :wq!)
git push -f (forced update)
_Whenever interactive mode is accessed, need to escape (:wq!)_

-- **merge conflicts**
1 git checkout main -> git pull
2 git checkout feature -> git merge main
3 Fix conflicts -> mostly accept current changes -> Stage the files (+)
_(incoming changes are from the branch being merged, conversely current changes are the modifications already present in your currently active branch)_
4 git rebase --continue -> commit msg(ignore) (Enter) -> (esc):wq! (CONTINUE TILL CONFLICTS EXIST)
5 git pull -> git push -f

-- **cherry-pick**
git cherry-pick <commitId> -> adds to current branch
git push

-- *in case of merge conflicts, push the changes, copy the commitId, 
create a new branch and cherry-pick onto it*