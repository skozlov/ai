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

## System requirements

[Java 8+](http://java.com/en/download/)

## Usage

Select types of agents you want to test by double-clicking on them in the main frame.

![main_frame](https://raw.githubusercontent.com/skozlov/ai/master/demo/main_frame.png)

As you can see, the program generates equal environments for all environments and visualizes them in separate frames:

![before_start](https://raw.githubusercontent.com/skozlov/ai/master/demo/before_start.png)

To start the worlds, click `Start` button in the main frame.
Now agents walk through their environments, and the main frame contains a table with the current values of the objective function:

![progress](https://raw.githubusercontent.com/skozlov/ai/master/demo/progress.png)

At the end, you can stop the worlds by clicking `Stop` button.
Then you are able to export statistics as a [CSV](https://en.wikipedia.org/wiki/Comma-separated_values) file with `;` as a delimiter.

![export_menu](https://raw.githubusercontent.com/skozlov/ai/master/demo/export_menu.png)

![export_dialog](https://raw.githubusercontent.com/skozlov/ai/master/demo/export_dialog.png)

Next, you can work with the CSV file via, for instance, [Microsoft Excel](https://products.office.com/excel)

![table](https://raw.githubusercontent.com/skozlov/ai/master/demo/table.png)

![chart](https://raw.githubusercontent.com/skozlov/ai/master/demo/chart.png)
