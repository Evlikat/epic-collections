package net.evlikat.epiccollections.util;

import java.util.function.Consumer;
import java.util.function.Function;

public class Either<L, R> {

    private interface Option<L, R> {

        <T> T fold(Function<L, T> leftHandler, Function<R, T> rightHandler);

        void consume(Consumer<L> leftConsumer, Consumer<R> rightConsumer);

        <T> Option<T, R> mapLeft(Function<L, T> leftMapper);

        <T> Option<L, T> mapRight(Function<R, T> rightMapper);

        Option<R, L> swap();
    }

    private static class Left<L, R> implements Option<L, R> {
        private final L value;

        Left(L value) {
            this.value = value;
        }

        @Override
        public <T> T fold(Function<L, T> leftHandler, Function<R, T> rightHandler) {
            return leftHandler.apply(value);
        }

        @Override
        public void consume(Consumer<L> leftConsumer, Consumer<R> rightConsumer) {
            leftConsumer.accept(value);
        }

        @Override
        public <T> Option<T, R> mapLeft(Function<L, T> leftMapper) {
            return new Left<>(leftMapper.apply(value));
        }

        @Override
        public <T> Option<L, T> mapRight(Function<R, T> rightMapper) {
            return new Left<>(value);
        }

        @Override
        public Option<R, L> swap() {
            return new Right<>(value);
        }
    }

    private static class Right<L, R> implements Option<L, R> {
        private final R value;

        Right(R value) {
            this.value = value;
        }

        @Override
        public <T> T fold(Function<L, T> leftHandler, Function<R, T> rightHandler) {
            return rightHandler.apply(value);
        }

        @Override
        public void consume(Consumer<L> leftConsumer, Consumer<R> rightConsumer) {
            rightConsumer.accept(value);
        }

        @Override
        public <T> Option<T, R> mapLeft(Function<L, T> leftMapper) {
            return new Right<>(value);
        }

        @Override
        public <T> Option<L, T> mapRight(Function<R, T> rightMapper) {
            return new Right<>(rightMapper.apply(value));
        }

        @Override
        public Option<R, L> swap() {
            return new Left<>(value);
        }
    }

    private final Option<L, R> inner;

    private Either(Option<L, R> inner) {
        this.inner = inner;
    }

    public static <L, R> Either<L, R> left(L some) {
        return new Either<>(new Left<L, R>(some));
    }

    public static <L, R> Either<L, R> right(R some) {
        return new Either<>(new Right<L, R>(some));
    }

    public <T> T fold(Function<L, T> leftHandler, Function<R, T> rightHandler) {
        return inner.fold(leftHandler, rightHandler);
    }

    public void consume(Consumer<L> leftConsumer, Consumer<R> rightConsumer) {
        inner.consume(leftConsumer, rightConsumer);
    }

    public <T> Either<T, R> mapLeft(Function<L, T> leftMapper) {
        return new Either<>(inner.mapLeft(leftMapper));
    }

    public <T> Either<L, T> mapRight(Function<R, T> rightMapper) {
        return new Either<>(inner.mapRight(rightMapper));
    }

    public Either<R, L> swap() {
        return new Either<>(inner.swap());
    }
}
