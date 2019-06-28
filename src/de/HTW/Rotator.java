package de.HTW;

import lenz.htw.cywwtaip.net.NetworkClient;
import lenz.htw.cywwtaip.world.GraphNode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;


 public class Rotator {




    public static Vector3D getNormal( NetworkClient client, int botNo, int Player ){

        return  new Vector3D (client.getBotPosition(Player,botNo)[0],client.getBotPosition(Player,botNo)[1],client.getBotPosition(Player,botNo)[2]) ;

    }


    public static Vector3D getTangent ( NetworkClient client, int botNo ){

        return  new Vector3D (client.getBotDirection(botNo)[0],client.getBotDirection(botNo)[1],client.getBotDirection(botNo)[2]) ;

    }


    public static Vector3D getUVector( Vector3D tangentVec , Vector3D normalVec) {

        return normalVec.crossProduct(tangentVec);

    }

    public static float getRotationAngle ( Vector3D destination, NetworkClient client, int botNo,int Player ){

        Vector3D a  = getTangent(client, botNo);
        double aMagnitude = a.getNorm();
        Vector3D n = getNormal(client, botNo, Player);
        Vector3D b = projectPointToPlane(destination,n); //lineIntersection(n,n,destination,n);
        Vector3D u = getUVector(a,n);
        double bMagnitude = b.getNorm();
        //System.out.println("PLAYER "+Player+ " b Length = "+bMagnitude);
        double ang = calcAng (a,b,aMagnitude,bMagnitude);


        if(Vector3D.dotProduct(u,b)>0){

            return (float)ang;
        }else{

            return (float)-ang;
        }
    }


    public static double calcAng( Vector3D a, Vector3D b, double aMagnitude, double bMagnitude){

       return java.lang.Math.acos(a.dotProduct(b)/(aMagnitude * bMagnitude));
    }

    /**
     * Determines the point of intersection between a plane defined by a point and a normal vector and a line defined by a point and a direction vector.
     *
     * @param planePoint    A point on the plane. Unsere Position
     * @param planeNormal   The normal vector of the plane.
     * @param linePoint     A point on the line.  ZIEL POSITION!!!!
     * @param lineDirection The direction vector of the line. AUCH NORMALE VON DER TANGENTIAL EBENE!!!
     * @return The point of intersection between the line and the plane, null if the line is parallel to the plane.
     */
    public static Vector3D lineIntersection(Vector3D planePoint, Vector3D planeNormal, Vector3D linePoint, Vector3D lineDirection) {
        if (planeNormal.dotProduct(lineDirection.normalize()) == 0) {
            return null;
        }
        double t = (planeNormal.dotProduct(planePoint) - planeNormal.dotProduct(linePoint)) / planeNormal.dotProduct(lineDirection.normalize());
        return linePoint.add(lineDirection.normalize().scalarMultiply(t));
    }

    public static Vector3D projectPointToPlane( Vector3D dest, Vector3D pos){
      return   dest.subtract(pos);
    }

/*

    Vektor in der Ebene = dest - (pos*dest)* pos

    dann winkel berechnune
 */

}
