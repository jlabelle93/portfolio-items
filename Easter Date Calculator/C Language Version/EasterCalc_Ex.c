/* Jacob Labelle 300306856 COSC 211 Ex 2-9 */
#include <stdio.h>

int main() {
    int M, Y, C, X, Z, D, G, E, N;
    printf("Enter the desired year: ");
    scanf("%d", &Y);
    G = (Y % 19) + 1;
    C = (Y / 100) + 1;
    X = (3 * C / 4) - 12;
    Z = ((8 * C + 5) / 25) - 5;
    D = (5 * Y / 4) - X - 10;
    E = (11 * G + 20 + Z - X) % 30;
    if((E == 25 && G > 11) || E == 24) E += 1;
    N = 44 - E;
    if(N < 21) N += 30;
    N = N + 7 - ((D + N) % 7);
    if(N > 31) {
        M = 4;
        N = N - 31;
    }
    else {
        M = 3;
    }
    printf("Easter Sunday is %d/%d/%d\n", N, M, Y);
    return 0;
}
