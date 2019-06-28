package de.HTW;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Target {
    //One energy spot(cut out rectangle)
    //0.95, -0.33,0)
    //0.95, 0.33,0)
    //0.95, 0,0.33)
    //0.95, 0, -0.33)
    //Vector3D Destination =  new Vector3D( 0.95,-0.33,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
    //check Vector3D Destination =  new Vector3D( 0,0.95,0);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);
    //check1 Vector3D Destination =  new Vector3D( 0,0,0.95);//myGraph[nodeNo].x,myGraph[nodeNo].y,myGraph[nodeNo].z);

    /**
     * somewhat working, needs improvements
     * @param pos
     * @return
     */
    public Vector3D giveClosestEnergy(float[] pos){
        double varpos[]={0,0,0};
        varpos[0] = pos[0];//Math.abs(pos.getX())-0.94;
        varpos[1] = pos[1];//Math.abs(pos.getY())-0.94;
        varpos[2] = pos[2];//Math.abs(pos.getZ())-0.94;
        int var=0;
        for(int i =0; i< 2;i++){
            if(varpos[i+1] >= varpos[i]){
                var = i;
            }
        }
       // varpos[var]
        //double max = Math.max(x,Math.max(y,z));
        Vector3D target = new Vector3D(0.95,0.33,0.33);
        //todo check in which direction (case0 y= -0.33 or 0.33)
        if(varpos[var]>0) {
            switch (var) {
                case 0: {
                    target = new Vector3D(0.95, 0, 0);
                    break;
                }
                case 1: {
                    target = new Vector3D(0, 0.95, 0);
                    break;
                }
                case 2: {
                    target = new Vector3D(0, 0, 0.95);
                    break;
                }
            }
        }else{
            switch (var) {
                case 0: {
                    target = new Vector3D(-0.95, 0, 0);
                    break;
                }
                case 1: {
                    target = new Vector3D(0, -0.95, 0);
                    break;
                }
                case 2: {
                    target = new Vector3D(0, 0, -0.95);
                    break;
                }
            }
        }
        return target;
    }


}
