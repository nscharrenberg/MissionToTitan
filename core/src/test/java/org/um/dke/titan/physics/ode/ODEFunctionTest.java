package org.um.dke.titan.physics.ode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.utils.GdxTestRunner;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(GdxTestRunner.class)
public class ODEFunctionTest {
    private double timeStep;
    private ODEFunction f;
/** Tests for the call method  **/
    @Test
    public void testTimeStepSmallerThanZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEFunction();

        timeStep = -10;

       assertThrows(IllegalArgumentException.class, () -> f.call(timeStep, state));
    }

    @Test
    public void testTimeStepEqualToZero() {
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEFunction();

        timeStep = 0;

       assertThrows(IllegalArgumentException.class, () -> f.call(timeStep, state));
    }
    //I have some doubts on this one since we are already testing what happens if we pass a null object to applyforces but yeah
    @Test
    public void movingObjectisNull() {
        MovingObject m = null;
        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
        f = new ODEFunction();

        timeStep = 0;

        assertThrows(NullPointerException.class, () -> f.call(timeStep, state));
    }
    @Test
    public void statePassedIsNull() {
        State state = null;
        timeStep = 0.5;
        f = new ODEFunction();

       assertThrows(NullPointerException.class, () -> f.call(timeStep, state));
    }
//    @Test
//    //I don't think i can really test thi one without applying all the forces from the planets in the system
//    public void computationsAreDoneCorretlyInTheCallMethod() {
//
//
//        MovingObject m = new MovingObject("Mars", 100, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
//        State state = new State(new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), new Vector3D(1, 1, 1), m);
//        m.setForce((new Vector3D(10, 10, 10)));
//        double timeStep = 1;
//        Vector3D rateAccelleration = new Vector3D(100/10, 100/10, 100/10);
//        Vector3dInterface velocity  = state.getVelocity();
//        Vector3dInterface rateVelocity = new Vector3D(velocity.getX() + rateAccelleration.getX() * timeStep, velocity.getY() + rateAccelleration.getY() * timeStep, velocity.getZ() + rateAccelleration.getZ() * timeStep);
//        Rate r = new Rate(rateAccelleration, rateVelocity);
//
//        ODEFunction f = new ODEFunction();
//        Rate returnedRAte = (Rate) f.call(timeStep, state);
//
//        System.out.print("The test rate of accelleration is: " + r.getAcceleration() );
//        System.out.print("The actual rate of accelleration is: " + returnedRAte.getAcceleration() );
//
//        assertEquals(r.getAcceleration(), returnedRAte.getAcceleration());
//        assertEquals(r.getVelocity(), returnedRAte.getVelocity());
//
//    }

    /** Tests for resetForces **/
    @Test
    public void listPassedIsNullResetForces() {
        List<MovingObject> l = null;
        f = new ODEFunction();
        assertThrows(NullPointerException.class, () -> f.resetForces(l));
    }

    //This is very trivial i have to say
    @Test
    public void listPassedIsNotNull() {

        ArrayList<MovingObject> l = new  ArrayList<MovingObject>();

        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject j = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject e = new MovingObject("Earth", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        m.setForce(new Vector3D(1,1,1)); j.setForce(new Vector3D(1,1,1)); e.setForce(new Vector3D(1,1,1));
        l.add(m); l.add(j); l.add(e);
        f = new ODEFunction();
        f.resetForces(l);


        ArrayList<MovingObject> list = new ArrayList<MovingObject>();
        MovingObject mars = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject jupi = new MovingObject("Jupiter", 5.5F, 7, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        MovingObject earth = new MovingObject("Earth", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));
        mars.setForce(new Vector3D(0,0,0)); jupi.setForce(new Vector3D(0,0,0)); earth.setForce(new Vector3D(0,0,0));
        //what happens is that the list is not adding elements; the implementation is empty
        list.add(mars); list.add(jupi);list.add(earth);


        System.out.println(list.get(1).getForce());

        assertEquals(list.get(0).getForce(), l.get(0).getForce());
        assertEquals(list.get(1).getForce(), l.get(1).getForce());
        assertEquals(list.get(2).getForce(), l.get(2).getForce());
    }

    /** Tests for addForcesToPlanets **/
    @Test
    public void listPassedIsNullAddForcesToPlanets() {
        List<MovingObject> l = null;
        MovingObject m = new MovingObject("Mars", 5.5F, 6, new Vector3D(1, 1, 1),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        assertThrows(NullPointerException.class, () -> f.addForcesToPlanets(m, l));
    }

    @Test
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
        assertThrows(NullPointerException.class, () -> f.addForcesToPlanets(m, l));
    }

    @Test
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

        assertEquals(jupiter1.getForce(), new Vector3D(1,1,1));
        assertEquals(jupiter2.getForce(), new Vector3D(1,1,1));
        assertEquals(jupiter3.getForce(), new Vector3D(1,1,1));
        assertEquals(m.getForce(), new Vector3D(1,1,1));
    }

    /**Testing the newtonsLaw method**/
    @Test
    public void twoNonNullObjects() {
        double G = 6.67408e-11;
        MovingObject j = new MovingObject("Jupiter", 10, 7, new Vector3D(9, 9, 9),1, new Vector3D(1, 1, 1));
        MovingObject m = new MovingObject("Mars", 10, 6, new Vector3D(10, 10, 10),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();

        Vector3dInterface v = j.getPosition().sub(m.getPosition());
        double grav = j.getMass() * m.getMass() * G;
        double norm_third = Math.pow(v.norm(), 3);
        assertEquals(f.newtonsLaw(j, m), new Vector3D(v.getX() * (-grav/norm_third), v.getY() * (-grav/norm_third), v.getZ() * (-grav/norm_third)));
    }
    @Test
    public void oneNullObjectPassedToNewtonslaw() {

        MovingObject j = new MovingObject("Jupiter", 100, 7, new Vector3D(3, 3, 3),1, new Vector3D(1, 1, 1));
        MovingObject m = null;

        f = new ODEFunction();
        assertThrows(NullPointerException.class, () -> f.newtonsLaw(m, j));

    }
    @Test
    public void secondNullObjectPassedToNewtonslaw() {

        MovingObject j = null;
        MovingObject m = new MovingObject("Mars", 10, 6, new Vector3D(10, 10, 10),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        assertThrows(NullPointerException.class, () -> f.newtonsLaw(m, j));

    }
    @Test
    public void bothNullObjectsPassedToNewtonslaw() {

        MovingObject j = null;
        MovingObject m = null;

        f = new ODEFunction();
        assertThrows(NullPointerException.class, () -> f.newtonsLaw(m, j));

    }
    @Test
    public void oneObjectPassedToNewtonslawWithMassZero() {

        MovingObject j = new MovingObject("Jupiter", 0, 7, new Vector3D(3, 3, 3),1, new Vector3D(1, 1, 1));
        MovingObject m = new MovingObject("Mars", 10, 6, new Vector3D(10, 10, 10),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        assertThrows(IllegalArgumentException.class, () -> f.newtonsLaw(m, j));

    }
    @Test
    public void secondObjectPassedToNewtonslawWithMassZero() {

        MovingObject j = new MovingObject("Jupiter", 100, 7, new Vector3D(3, 3, 3),1, new Vector3D(1, 1, 1));
        MovingObject m = new MovingObject("Mars", 0, 6, new Vector3D(10, 10, 10),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        assertThrows(IllegalArgumentException.class, () -> f.newtonsLaw(m, j));

    }
    @Test
    public void bothObjectsPassedToNewtonslawWithMassZero() {

        MovingObject j = new MovingObject("Jupiter", 0, 7, new Vector3D(3, 3, 3),1, new Vector3D(1, 1, 1));
        MovingObject m = new MovingObject("Mars", 0, 6, new Vector3D(10, 10, 10),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        assertThrows(IllegalArgumentException.class, () -> f.newtonsLaw(m, j));

    }
    @Test
    public void twoNonNullObjectsWithDistanceEqualToZero() {

        MovingObject j = new MovingObject("Jupiter", 100, 7, new Vector3D(3, 3, 3),1, new Vector3D(1, 1, 1));
        MovingObject m = new MovingObject("Mars", 10, 6, new Vector3D(3, 3, 3),1, new Vector3D(1, 1, 1));

        f = new ODEFunction();
        assertThrows(IllegalArgumentException.class, () -> f.newtonsLaw(m, j));
    }

}

