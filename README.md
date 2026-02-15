# Traffic Simulation

---

## TL;DR

This is a simple Traffic Simulation that operates by cars, trucks, pedestrians (3 types of entities) and traffic lights. Each entity has it's own path and follows it. All entities respect each other so they won't just pass through (each other).

In core, the Simulation has a simple Simulation Engine which works with concrete Configuration and Graphics implementation. That means, you can create your own Configuration (`implements TrafficSimulationConfiguration`) and Graphics implementation (`extends TrafficSimulationGraphics`). Main laws of simulation are already described in Simulation Engine.

---

## Features

- Road traffic lights and Pedestrian traffic lights with customizable timers;
- Different unique velocities in [min, max) for all entities (car, truck, pedestrian);
- Smooth deceleration and aceleration for all entities;
- Different customizable paths that unique for each type of entity;
- All entities have simple AI that controls their moves ("follow the path", "let the pedestrian go", "don't crush into other road user" etc.).

---

## UI

User interface has 3 control buttons:
- "Restart": restarts the simulation;
- "Spawn a car": randomly spawns a new car or truck;
- "Spawn a pedestrian": randomly spawns a new pedestrian.

---

## Screenshots

![Default mode](images/default_mode.png)
*Default mode*

![Show coordinates mode](images/show_coordinates_mode.png)
*Show coordinates mode*

![Show max velocities mode](images/show_max_velocities_mode.png)
*Show max velocities mode*

![Show velocities mode](images/show_velocities_mode.png)
*Show velocities mode*

![Show directions mode](images/show_directions_mode.png)
*Show directions mode*

![Show paths mode](images/show_paths_mode.png)
*Show paths mode*
