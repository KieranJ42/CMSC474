import subprocess

board: list[list[int]] = [
    [0, 0],
    [0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 2, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
]


def swap(b: list[list[int]]):
    for row in range(1, 11):
        for col in range(1, 2 * row):
            if b[row - 1][col - 1] == 1:
                b[row - 1][col - 1] = 2
            elif b[row - 1][col - 1] == 2:
                b[row - 1][col - 1] = 1


def board_to_string(b: list[list[int]]):
    s = ""
    for row in range(1, 11):
        s += (" " * (2 * (10 - row))) + " ".join([str(i) for i in b[row - 1]]) + "\n"
    return s


def string_to_board(s: str) -> list[list[int]]:
    return [[int(entry) for entry in line.split()] for line in s.splitlines()]


def evaluate_end(b: list[list[int]]):
    score_1 = sum([sum([cell == 1 for cell in row]) for row in b])
    score_2 = sum([sum([cell == 2 for cell in row]) for row in b])
    print(f"Final score: {score_1} to {score_2}")
    print(board_to_string(b))
    exit()


error_count = 0
player_1s_turn = True

subprocess.run(["javac", "reversi.java"])

while True:
    print(board_to_string(board))
    result = subprocess.run(
        ["java", "reversi"],
        input=board_to_string(board),
        capture_output=True,
        text=True,
        env={"DEBUG_OTHELLO_PRINT_BOARD": "1"},
    )
    print(result.stdout)
    print(result.stderr)

    if result.returncode == 0:
        error_count = 0
        board = string_to_board(result.stdout)

    else:
        error_count += 1

    if error_count >= 2:
        if not player_1s_turn:
            swap(board)
        evaluate_end(board)

    swap(board)
    player_1s_turn = not player_1s_turn