contents = ""
from colorama import Fore, Back, Style


while(True):
    line = input("Enter the file path: ")
    contents += line + "\n"

    if (line == ""):
        break

splitted = contents.split("\n")
splitted = [line for line in splitted if len(line) > 4]

print(Fore.GREEN)
for i  in range(0, len(splitted), 2):
    string = splitted[i]
    if (string[:1].islower()):
        print('- ', end = '')
    else:
        print('+ ', end = '')
    print(string)