input_file = "data/da1.txt"
output_file = "output.txt"

with open(input_file, "r") as file:
    lines = file.readlines()

modified_lines = ["- " + line.strip() + "\n" for line in lines]

with open(output_file, "w") as file:
    file.writelines(modified_lines)