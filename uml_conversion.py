contents = ""
from colorama import Fore, Back, Style


while(True):
    line = input(">>: ")
    contents += line + "\n"

    if (line == ""):
        break

symbols = contents.split("\n")

valid_symbols = []

for symbol in symbols:
    # method
    if (symbol in valid_symbols):
        continue

    if ("()" in symbol):
        valid_symbols.append(symbol)
        continue

    # if method
    elif "(" and ")" in symbol:
        parameters = symbol.split("(")[1].split(")")[0].split(",")
        parameters = [param.strip() for param in parameters]
        print (parameters)

        if (" " not in parameters[0]):
            continue
        name = symbol.split("(")[0]
        returnType = symbol.split(":")

        parametersFormatted = [param.split(" ") for param in parameters]
        parametersFormatted = [f"{param[1]}:{param[0]}" for param in parametersFormatted]


        final_symbol = f"{name}({', '.join(parametersFormatted)})"


        if (len(returnType) >= 2):
            final_symbol = f"{final_symbol} : {returnType[1].strip()}"
        valid_symbols.append(final_symbol)

    # if attribute
    elif (":" in symbol):
        valid_symbols.append(symbol)
        

for valid_symbol in valid_symbols:
    if (valid_symbol[0].isupper()):
        print(Fore.BLUE + "+ " + valid_symbol, end="")
    else:
        print(Fore.RED + "- " + valid_symbol, end="")
        
    print(Style.RESET_ALL)        