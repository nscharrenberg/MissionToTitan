package org.um.dke.titan.interfaces;


public interface DataInterface {
    StateInterface[][] getData(ODEFunctionInterface f, double tf, double h);
}
