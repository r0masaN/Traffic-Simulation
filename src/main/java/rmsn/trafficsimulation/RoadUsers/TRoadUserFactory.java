package rmsn.trafficsimulation.RoadUsers;

import rmsn.trafficsimulation.Simulation.TrafficSimulation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TRoadUserFactory {
    private static final Map<Class<? extends TRoadUser>, Supplier<? extends TRoadUser>> FACTORIES = new HashMap<>();

    static {
        FACTORIES.put(TCar.class, TCar::new);
        FACTORIES.put(TTruck.class, TTruck::new);
        FACTORIES.put(TPedestrian.class, TPedestrian::new);
    }

    public static boolean generateRandomRoadUserIntoTransportSimulationContext(final Class<? extends TRoadUser> clazz,
                                                                               final TrafficSimulation ctx,
                                                                               final boolean isUserGeneration) {
        final Supplier<? extends TRoadUser> supplier = FACTORIES.get(clazz);
        if (supplier == null)
            throw new IllegalArgumentException(String.format("Unsupported traffic user: %s", clazz));

        if (!isUserGeneration && !ctx.canRandomlyGenerateNewRoadUser())
            return false;
        else if (isUserGeneration && !ctx.canAddNewRoadUser())
            return false;

        final TRoadUser roadUser = supplier.get();
        return roadUser.setupAndAddIntoContext(ctx.getContext());
    }
}
