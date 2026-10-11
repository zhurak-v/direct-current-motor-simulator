package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.core.ports.services.AnnotationHandler;
import com.university.direct_current_motor.core.ports.services.ClassScanner;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassScannerImpl implements ClassScanner {

    private static final String DEFAULT_BASE_PACKAGE;

    static {
        DEFAULT_BASE_PACKAGE = "com.university.direct_current_motor";
    }

    private static final class Holder {

        private static final ClassScanner INSTANCE;

        static {
            INSTANCE = new ClassScannerImpl();
        }

        private Holder() {
            throw new UnsupportedOperationException();
        }

    }

    public static ClassScanner getClassScanner() {
        return Holder.INSTANCE;
    }

    private final Map<Class<? extends Annotation>, AnnotationHandler> handlers;
    private final Map<AnnotationHandler, Set<Class<?>>> processed;

    private ClassScannerImpl() {
        this.handlers = new LinkedHashMap<>();
        this.processed = new IdentityHashMap<>();
    }

    @Override
    public <A extends Annotation> void registerHandler(
            final Class<A> annotationClass,
            final AnnotationHandler handler
    ) {
        Objects.requireNonNull(annotationClass);
        Objects.requireNonNull(handler);
        this.handlers.put(annotationClass, handler);
    }

    @Override
    public void scan() {
        scan(DEFAULT_BASE_PACKAGE);
    }

    @Override
    public void scan(final String basePackage) {
        Objects.requireNonNull(basePackage);
        final List<Class<?>> classes = new ArrayList<>();
        try {
            this.doScan(basePackage, classes);
            for (final Class<?> clazz : classes) {
                for (final Map.Entry<Class<? extends Annotation>, AnnotationHandler> entry : this.handlers.entrySet()) {
                    final AnnotationHandler handler = entry.getValue();

                    if (!clazz.isAnnotationPresent(entry.getKey())) {
                        continue;
                    }

                    final Set<Class<?>> handledClasses =
                            this.processed.computeIfAbsent(handler, k -> new HashSet<>());

                    if (handledClasses.contains(clazz)) {
                        continue;
                    }

                    handler.handle(clazz);
                    handledClasses.add(clazz);
                }
            }
        } catch (final Exception ex) {
            throw new RuntimeException("Scan failed", ex);
        }
    }

    private void doScan(
            final String basePackage,
            final List<Class<?>> result
    ) throws IOException {
        Objects.requireNonNull(basePackage);
        Objects.requireNonNull(result);

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String path = basePackage.replace('.', '/');
        final Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            final URL resource = resources.nextElement();
            final String protocol = resource.getProtocol();

            if ("file".equals(protocol)) {
                scanFileSystem(new File(resource.getFile()), basePackage, result);
            } else if ("jar".equals(protocol)) {
                scanJar(resource, basePackage, result);
            }
        }
    }

    private void scanFileSystem(
            final File directory,
            final String basePackage,
            final List<Class<?>> result
    ) {
        Objects.requireNonNull(basePackage);
        Objects.requireNonNull(directory);
        Objects.requireNonNull(result);

        final File[] files = directory.listFiles();
        if (Objects.isNull(files)) {
            return;
        }

        for (final File file : files) {
            if (file.isDirectory()) {
                scanFileSystem(file, basePackage + "." + file.getName(), result);
            } else if (file.getName().endsWith(".class")) {
                tryLoad(basePackage + "." + file.getName().replace(".class", ""), result);
            }
        }
    }

    private void scanJar(
            final URL resource,
            final String basePackage,
            final List<Class<?>> result
    ) throws IOException {
        Objects.requireNonNull(resource);
        Objects.requireNonNull(basePackage);
        Objects.requireNonNull(result);

        final String jarPath = resource.getPath().replaceFirst("file:", "").replaceFirst("!.*", "");
        try (final JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
            final Enumeration<JarEntry> entries = jar.entries();
            final String prefix = basePackage.replace('.', '/') + "/";

            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String name = entry.getName();
                if (name.endsWith(".class") && name.startsWith(prefix)) {
                    tryLoad(name.replace('/', '.').replace(".class", ""), result);
                }
            }
        }
    }

    private void tryLoad(final String className, final List<Class<?>> result) {
        Objects.requireNonNull(className);
        Objects.requireNonNull(result);
        try {
            final Class<?> clazz = Class.forName(className);
            if (!clazz.isInterface()) {
                result.add(clazz);
            }
        } catch (ClassNotFoundException ignored) {}
    }
}