package cg.gl3d.object;

import javax.media.opengl.GL;

public interface Object3D {
	public void draw(GL gl, int translateX, int translateY, int translateZ);
	public void draw(GL gl, int translateX, int translateY, int translateZ, float red, float green, float blue);
}
