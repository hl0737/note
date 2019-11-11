set /p msg=
git st
git add .
git cm "%msg%"
git push
pause