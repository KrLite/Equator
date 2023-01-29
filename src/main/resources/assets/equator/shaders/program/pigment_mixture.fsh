#version 150

#ifdef GL_ES
precision highp float;
#endif

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec4 Coord;
uniform vec4 ColorLeftTop;
uniform vec4 ColorLeftBottom;
uniform vec4 ColorRightBottom;
uniform vec4 ColorRightTop;
uniform float MixRatio;

out vec4 fragColor;

#include "mixbox.glsl" // paste the contents of mixbox.glsl here

void main(void)
{
    vec3 rgb1 = vec3(0, 0.129, 0.522); // blue
    vec3 rgb2 = vec3(0.988, 0.827, 0); // yellow
    float t = 0.5;                     // mixing ratio

    vec3 rgb = mixbox_lerp(rgb1, rgb2, t);

    gl_FragColor = vec4(rgb, 1.0);
}
