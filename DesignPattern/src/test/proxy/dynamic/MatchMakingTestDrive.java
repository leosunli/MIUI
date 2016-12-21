package proxy.dynamic;

import java.lang.reflect.Proxy;

/**
 * Created by mi on 16-12-21.
 */
public class MatchMakingTestDrive {

    public static void main(String[] args) {
        MatchMakingTestDrive test = new MatchMakingTestDrive();
        test.drive();
    }

    public void drive() {
        PersonBean person = new PersonBeanImpl();
        person.setName("leo");
        person.setGender("male");
        person.setHotOrNotRating(20);
        person.setInterests("football");

        PersonBean ownerProxy = getOwnerProxy(person);
        System.out.println("Name is " + ownerProxy.getName());
        ownerProxy.setInterests("bowling, Go");
        System.out.println("interests set from owner proxy");

        try {
            ownerProxy.setHotOrNotRating(10);
        } catch (Exception e) {
            System.out.println("can not set rating from owner proxy");
        }

    }

    PersonBean getOwnerProxy(PersonBean personBean) {
        return (PersonBean) Proxy.newProxyInstance(
                personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new OwnerInvocationHandler(personBean));
    }
}
