package net.uku3lig.ukutils.util;

import net.uku3lig.ukutils.Ukutils;
import net.uku3lig.ukutils.commands.UkutilsCommand;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;

public class ReflectionsUtil {
    public static List<? extends UkutilsCommand> getCommands(Ukutils plugin) {
        return new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(UkutilsCommand.class.getPackageName()))
                .filterInputsBy(new FilterBuilder().includePackage(UkutilsCommand.class.getPackageName()))
                .setScanners(Scanners.SubTypes))
                .getSubTypesOf(UkutilsCommand.class)
                .stream()
                .filter(klass -> !(klass.isInterface() || Modifier.isAbstract(klass.getModifiers())))
                .map(klass -> {
                    try {
                        return getConstructor(klass).newInstance(plugin);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();
    }

    private static <T> Constructor<? extends T> getConstructor(Class<? extends T> klass) throws NoSuchMethodException {
        try {
            return klass.getConstructor(Ukutils.class);
        } catch (Exception e) {
            return klass.getDeclaredConstructor(Ukutils.class);
        }
    }

    private ReflectionsUtil() {}
}
