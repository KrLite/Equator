#version 150

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 LeftTop;
uniform vec2 LeftBottom;
uniform vec2 RightBottom;
uniform vec2 RightTop;

out vec4 fragColor;

float cross_with(vec2 a, vec2 b) {
	return (b.x - a.x) * (texCoord.y - a.y) - (b.y - a.y) * (texCoord.x - a.x);
}

bool is_in_rectangle(vec2 a, vec2 b, vec2 c, vec2 d) {
	return cross_with(b, a) * cross_with(d, c) >= 0.0 && cross_with(a, d) * cross_with(c, b) >= 0.0;
}

void main() {
	fragColor =
}
