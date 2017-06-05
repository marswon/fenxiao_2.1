rem 放根目录下执行 清楚svn信息
for /r . %%a in (.) do @if exist "%%a\.svn" rd /s /q "%%a\.svn"