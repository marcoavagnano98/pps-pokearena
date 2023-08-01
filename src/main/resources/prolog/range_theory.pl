% range/3(+MinValue, +IncrementOfRange, -RangeBetweenMinMax)
% Returns range between MIN given value and MIN + INC
range(MIN, INC, range(MIN, MAX)) :- MAX is MIN + INC .
% generateLevels/3(+difficulty, +nLevels, -listOfRange)
% Returns a list of BST range for each levels, according on the algorithm below
generateLevels(_,0,[]) :- !.
generateLevels(D, CL, [H | T])  :-
CLU is CL - 1,
MIN is (100 * D) + (300 / CL) ,
INC is 120,
range(MIN, INC, H),
generateLevels(D, CLU, T).