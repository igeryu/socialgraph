# Social Graph

This application takes an input file of the following format:

    john;peter;mary;susan;george;debbie;tom;bill;
    #
    0 1 2
    1 0 2
    2 0 1 3 4 6
    3 2 4 5
    4 2 3
    5 3 6
    6 2 5 7
    7 6

Where each person in the social graph is listed on the first line, and on the 3rd and subsequent lines, the adjacencies of those people are indicated. (Ex. John is connected to Peter and Mary).