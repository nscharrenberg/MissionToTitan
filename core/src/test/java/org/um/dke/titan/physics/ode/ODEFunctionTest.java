package org.um.dke.titan.physics.ode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ODEFunctionTest {
    private double timeStep;
    private ODEFunction f;
/** Tests for the call method  **/
    @Test
    @DisplayName("Testing the method call passing a time step smaller than zero")
    public void testTimeStepSmallerThanZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEFunction();

        timeStep = -10;

       Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    @DisplayName("Testing the method call passing a time step equal to zero")
    public void testTimeStepEqualToZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEFunction();

        timeStep = 0;

       Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    //I have some doubts on this one since we are already testing what happens if we pass a null object to applyforces but yeah
    @Test
    @DisplayName("Testing the method call passing a null moving object")
    public void movingObjectisNull() {
        MovingObject m = null;
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEFunction();

        timeStep = 0;

        Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    @DisplayName("Testing the method call passing a null state")
    public void statePassedIsNull() {
        State state = null;
        timeStep = 0.5;
        f = new ODEFunction();

       Assertions.assertThrows(RuntimeException.class, () -> f.call(timeStep, state));
    }
    @Test
    @DisplayName("Checking for a time step equal to zero")
    public void computationsAreDoneCorretlyInTheCallMethod() {
        MovingObject m = new MovingObject("Mars", 100, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        m.setForce((new Vector3D(10, 10, 10)));
        timeStep = 1;
        Vector3D rateAccelleration = new Vector3D(100/10, 100/10, 100/10);
        Vector3dInterface velocity  = state.getVelocity();
        Vector3dInterface rateVelocity = new Vector3D(velocity.getX() + rateAccelleration.getX() * timeStep, velocity.getY() + rateAccelleration.getY() * timeStep, velocity.getZ() + rateAccelleration.getZ() * timeStep);
        Rate r = new Rate(rateAccelleration, rateVelocity);

        f = new ODEFunction();

        assertEquals(r, f.call(timeStep, state));
    }

    /** Tests for resetForces **/
    @Test
    @DisplayName("Testing the method resetForces passing a null list f moving objects")
    public void listPassedIsNullResetForces() {
        List<MovingObject> l = null;
        f = new ODEFunction();
        Assertions.assertThrows(RuntimeException.class, () -> f.resetForces(l));
    }
    //This is very trivial i have to say
    @Test
    @DisplayName("Testing the method resetForces passing a not null list f moving objects and cheking if the forces of all objects are set to zero")
    public void listPassedIsNotNull() {
        List<MovingObject> l = new List<MovingObject>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<MovingObject> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(MovingObject movingObject) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public MovingObject get(int index) {
                return null;
            }

            @Override
            public MovingObject set(int index, MovingObject element) {
                return null;
            }

            @Override
            public void add(int index, MovingObject element) {

            }

            @Override
            public MovingObject remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<MovingObject> listIterator() {
                return null;
            }

            @Override
            public ListIterator<MovingObject> listIterator(int index) {
                return null;
            }

            @Override
            public List<MovingObject> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject j = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject e = new MovingObject("Earth", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        m.setForce(new Vector3D(1,1,1)); j.setForce(new Vector3D(1,1,1)); e.setForce(new Vector3D(1,1,1));
        l.add(m); l.add(j); l.add(e);
        f = new ODEFunction();
        f.resetForces(l);

        List<MovingObject> list = new List<MovingObject>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<MovingObject> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(MovingObject movingObject) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public MovingObject get(int index) {
                return null;
            }

            @Override
            public MovingObject set(int index, MovingObject element) {
                return null;
            }

            @Override
            public void add(int index, MovingObject element) {

            }

            @Override
            public MovingObject remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<MovingObject> listIterator() {
                return null;
            }

            @Override
            public ListIterator<MovingObject> listIterator(int index) {
                return null;
            }

            @Override
            public List<MovingObject> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        MovingObject mars = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject jupi = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject earth = new MovingObject("Earth", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        mars.setForce(new Vector3D(0,0,0)); jupi.setForce(new Vector3D(0,0,0)); earth.setForce(new Vector3D(0,0,0));

        assertEquals(l, list);
    }
    /** Tests for addForcesToPlanets **/
    @Test
    @DisplayName("Testing the method addForcesToPlanets passing a null list f moving objects")
    public void listPassedIsNullAddForcesToPLanets() {
        List<MovingObject> l = null;
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        Assertions.assertThrows(RuntimeException.class, () -> f.addForcesToPlanets(m, l));
    }
    @Test
    @DisplayName("Testing the method addForcesToPlanets passing a null list f moving objects")
    public void movingObjectPassedIsNullAddForcesToPLanets() {
        List<MovingObject> l = new List<MovingObject>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<MovingObject> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(MovingObject movingObject) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public MovingObject get(int index) {
                return null;
            }

            @Override
            public MovingObject set(int index, MovingObject element) {
                return null;
            }

            @Override
            public void add(int index, MovingObject element) {

            }

            @Override
            public MovingObject remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<MovingObject> listIterator() {
                return null;
            }

            @Override
            public ListIterator<MovingObject> listIterator(int index) {
                return null;
            }

            @Override
            public List<MovingObject> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        MovingObject j = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject e = new MovingObject("Earth", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        l.add(j); l.add(e);
        MovingObject m = null;

        f = new ODEFunction();
        Assertions.assertThrows(RuntimeException.class, () -> f.addForcesToPlanets(m, l));
    }
    @Test
    @DisplayName("Testing the method addForcesToPlanets passing a list moving objects containing planets that are all named  in the same way as the one passed as argument")
    public void movingObjectPassedIsNamedAsAllTheOtherPlanetsInTheList() {
        List<MovingObject> l = new List<MovingObject>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<MovingObject> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(MovingObject movingObject) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends MovingObject> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public MovingObject get(int index) {
                return null;
            }

            @Override
            public MovingObject set(int index, MovingObject element) {
                return null;
            }

            @Override
            public void add(int index, MovingObject element) {

            }

            @Override
            public MovingObject remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<MovingObject> listIterator() {
                return null;
            }

            @Override
            public ListIterator<MovingObject> listIterator(int index) {
                return null;
            }

            @Override
            public List<MovingObject> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        MovingObject jupiter1 = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject jupiter2 = new MovingObject("Jupiter", 5.5F, 6, new Vector3D(124, 1, 1),1, new Vector3D(1, 999999, 1));
        MovingObject jupiter3 = new MovingObject("Jupiter", 5.5F, 9, new Vector3D(124, 1, 1),1, new Vector3D(1, 999999, 1));

        l.add(jupiter1); l.add(jupiter2); l.add(jupiter3);

        jupiter1.setForce(new Vector3D(1,1,1));jupiter2.setForce(new Vector3D(1,1,1));jupiter3.setForce(new Vector3D(1,1,1));

        MovingObject m = new MovingObject("Jupiter", 888, 9, new Vector3D(124, 1, 1),1, new Vector3D(1, 9999, 72));
        m.setForce(new Vector3D(1,1,1));

        f = new ODEFunction();
        Assertions.assertAll(
                () -> assertEquals(jupiter1.getForce(), new Vector3D(1,1,1)),
                () -> assertEquals(jupiter2.getForce(), new Vector3D(1,1,1)),
                () -> assertEquals(jupiter3.getForce(), new Vector3D(1,1,1)),
                () -> assertEquals(m.getForce(), new Vector3D(1,1,1))
        );
    }
    /**Testing the newtonsLaw method**/

    @Test
    @DisplayName("Testing the method newTownsLaw passing two differently named and non null objects with diffent masses and positions")
    public void twoNonNullObjects() {

        MovingObject j = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(33, 33, 33),1, new Vector3D(1, 999999, 1));

        j.setForce(new Vector3D(1,1,1));m.setForce(new Vector3D(1,1,1));

        f = new ODEFunction();
        Assertions.assertAll(
                () -> assertEquals(j.getForce(), new Vector3D(1,1,1)),
                () -> assertEquals(m.getForce(), new Vector3D(1,1,1)));
    }

}

