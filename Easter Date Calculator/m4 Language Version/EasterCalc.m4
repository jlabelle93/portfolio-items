! Jacob Labelle 300306856 COSC211 Easter Calculator
divert(-1)
define(Y, 2025)
define(Y_r, %i0)
define(G_r, %l0)
define(C_r, %l1)
define(X_r, %l2)
define(Z_r, %l3)
define(D_r, %l4)
define(E_r, %l5)
define(N_r, %l6)
define(M_r, %l7)
divert
		.global main
	main:
		save    %sp, 96, %sp
		mov     Y, Y_r             ! Year to calculate moves to %i0
		mov     Y_r, %o0           ! G = (Y%19) + 1;
		call    .rem
		mov     19, %o1
		mov     %o0, G_r
		add     G_r, 1, G_r
		mov     Y_r, %o0           ! C = (Y / 100) + 1;
		call    .div
		mov     100, %o1
		mov     %o0, C_r
		add     C_r, 1, C_r
		mov     C_r, %o0           ! X = (3 * C / 4) - 12;
		call    .mul
		mov     3, %o1
		call    .div
		mov     4, %o1
		mov     %o0, X_r
		sub     X_r, 12, X_r
		mov     C_r, %o0           ! Z = ((8 * C + 5) / 25) - 5;
		call    .mul
		mov     8, %o1
		mov     %o0, Z_r
		add     Z_r, 5, Z_r
		mov     Z_r, %o0
		call    .div
		mov     25, %o1
		mov     %o0, Z_r
		sub     Z_r, 5, Z_r
		mov     Y_r, %o0           ! D = (5 * Y / 4) - X - 10;
		call    .mul
		mov     5, %o1
		call    .div
		mov     4, %o1
		mov     %o0, D_r
		sub     D_r, X_r, D_r
		sub     D_r, 10, D_r
		mov     G_r, %o0           ! E = (11 * G + 20 + Z - X) % 30;
		call    .mul
		mov     11, %o1
		mov     %o0, E_r
		add     E_r, 20, E_r
		add     E_r, Z_r, E_r
		sub     E_r, X_r, E_r
		mov     E_r, %o0
		call    .rem
		mov     30, %o1
		mov     %o0, E_r
		cmp     E_r, 25            ! if((E == 25 && G > 11) || E == 24) E += 1;
		bne     compare_E_24
		nop
		cmp     G_r, 11
		bg      increment_E
		nop
	  compare_E_24:
		cmp     E_r, 24
		bne     calc_N
		nop
	  increment_E:
		add     E_r, 1, E_r
	  calc_N:
		mov     44, N_r            ! N = 44 - E;
		sub     N_r, E_r, N_r
		cmp     N_r, 21            ! if(N < 21) N += 30;
		bge     continue_N
		nop
		add     N_r, 30, N_r
	  continue_N:
		add     N_r, 7, N_r        ! N = N + 7 - ((D + N) % 7);
		add     N_r, D_r, %o0
		call    .rem
		mov     7, %o1
		sub     N_r, %o0, N_r
		cmp     N_r, 31
		ble     march              ! if(N > 31)
		nop
		mov     4, M_r             ! M = 4;
		sub     N_r, 31, N_r       ! N = N - 31;
		ba      out
		nop
	  march:
		mov     3, M_r             ! else M = 3;
	  out:
		mov     1, %g1             ! exit program
		ta      0
