# Jewdle-Solver

Jewdle is a variant of the game Wordle which uses 6 letter words and pulls from various languages rather than just English. The idea behind this alogirithm is to use a brute force approach along with some basic frequency data of the position of characters in 6 letter words to curate a weighted list of words on each guessing turn. The list adjusts based on Jewdle feedback and updates accordingly. Weights are adjusted so that words with repeated letters are not as valuable. The algorithm also accounts for situations when multiple of the same character are in a word.
