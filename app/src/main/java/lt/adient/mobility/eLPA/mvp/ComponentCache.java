package lt.adient.mobility.eLPA.mvp;

import android.support.v4.util.SimpleArrayMap;

import java.util.UUID;

final class ComponentCache<C> {
    private final SimpleArrayMap<UUID, C> fragmentComponents;
    private final C activityComponent;

    ComponentCache(C activityComponent) {
        fragmentComponents = new SimpleArrayMap<>();
        this.activityComponent = activityComponent;
    }

    final C getFragmentComponent(UUID uuid) {
        return fragmentComponents.get(uuid);
    }

    final void removeComponent(UUID uuid) {
        fragmentComponents.remove(uuid);
    }

    final UUID putFragmentComponent(C component) {
        final UUID uuid = UUID.randomUUID();
        fragmentComponents.put(uuid, component);
        return uuid;
    }

    final C getActivityComponent() {
        return activityComponent;
    }
}
