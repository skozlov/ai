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

At each moment, the agent can perceive temperature in the cell it is at and react by going to north/south/west/east or remaining in place. The agent can walk only within the environment, i.e. if it is at, for instance, the northernmost cell and tries to go to north, it remains in place.

The objective function of the agent is the sum of all values of temperature that has been perceived by the agent. The agent seeks to maximize the objective function.

## Provided agent types

 - <i>ChaoticAgent</i> performs random actions.
 - <i>SpecializedAgent</i> uses an (asymptotically) optimal algorithm to reach the warmest cell and stay there.
