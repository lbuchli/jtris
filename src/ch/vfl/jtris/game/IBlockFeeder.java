package ch.vfl.jtris.game;

@FunctionalInterface
interface IBlockFeeder {
    Block generateNext();
}
