package decorator;

/**
 * 装饰者模式：动态将责任附加到对象上，若要扩展动能，
 * 装饰者提供了比继承更有弹性的替代方案
 */
public abstract class CondimentDecorator extends Beverage {
    public abstract String getDescription();
}
