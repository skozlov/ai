# ai
A two-dimensional sandbox for intelligent agents

## Environment

An intelligent agent lives in a two-dimensional discrete environment with discrete time.
The environment contains `n` rows and `n` columns, where `n` is a random number from `3` to `10`.
The agent is in a random cell at the initial time.

Each cell contains a non-negative integer number called <b><i>temperature</i></b>.
Exactly one random cell (called <b><i>epicenter</i></b>) has the maximum temperature (`Tmax`).
Each other cell has temperature calculated by this formula:

`T = Tmax - (|X - Xepic| + |Y - Yepic|)`, 
where `X` and `Y` are coordinates of the cell, 
`Xepic` and `Yepic` are coordinates of the epicenter.

The furthest cells from the epicenter has temperature of `0`.

Example (the agent is represented by `@`):

![environment](https://raw.githubusercontent.com/skozlov/ai/master/demo/environment.png)
