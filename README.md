# AVL-tree-program
# English-Spanish Translation AVL Tree Program

## Overview
This program efficiently translates English words into Spanish using an AVL (Adelson-Velsky and Landis) Tree. The use of an AVL tree ensures that the tree remains balanced at all times, providing optimal lookup times. The program reads word pairs from a CSV file, building a balanced binary tree to facilitate rapid access to translations. This implementation focuses on speed and efficiency, crucial for applications requiring quick lookup of information, such as language translation tools.

## Key Features
- **AVL Tree for Fast Lookups:** Ensures that the tree remains balanced with operations to adjust balances automatically after insertions, maintaining logarithmic search time complexity.
- **CSV-Driven Data Input:** Allows easy extension of the vocabulary by updating the CSV file, no need to alter the source code.
- **Interactive Translation Session:** Users can input sentences in English and receive their translations in Spanish in real-time.
- **Optimized Search Efficiency:** By maintaining minimal tree height, the program reduces the average search path, making translations faster.

## How It Works
The program uses an AVL Tree, which is a type of self-balancing binary search tree. Each node of the tree contains an English word and its corresponding Spanish translation. The AVL tree maintains a balance factor (the height difference between the left and right subtree) of -1, 0, or +1 for every node to ensure the tree remains balanced, thereby guaranteeing that the tree height is kept to a minimum. This results in improved search times, as the distance from the root to any given node is minimized.
