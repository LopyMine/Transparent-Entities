package net.lopymine.ed.dithering.vanilla;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VanillaDitherShaderPatcher {

	private static final String MARKER = "// transparent_entities:dithering";

	private static final Pattern MAIN_PATTERN = Pattern.compile("void\\s+main\\s*\\(\\s*(?:void)?\\s*\\)\\s*\\{");

	private static final String DITHER_BODY = """
		layout(std140) uniform DitheringData {
		    float DitherMinValue;
		    float DitherPixelSize;
		    float DitherNearDistance;
		    float DitherFarDistance;
		};

		float de_easeInOutCubic(float x) {
		    return x < 0.5 ?
		        4.0 * x * x * x :
		        1.0 - pow(-2.0 * x + 2.0, 3.0) / 2.0;
		}

		const mat4 DE_DITHER_MAT = mat4(
		    1.0 / 17.0,  9.0 / 17.0,  3.0 / 17.0,  11.0 / 17.0,
		    13.0 / 17.0, 5.0 / 17.0,  15.0 / 17.0, 7.0 / 17.0,
		    4.0 / 17.0,  12.0 / 17.0, 2.0 / 17.0,  10.0 / 17.0,
		    16.0 / 17.0, 8.0 / 17.0,  14.0 / 17.0, 6.0 / 17.0
		);

		void de_applyDitherMatrix(float visibility, vec2 fragCoord, float ditherPixelSize) {
		    vec2 cell = fragCoord / ditherPixelSize;
		    int x = int(cell.x);
		    int y = int(cell.y);
		    if (visibility < DE_DITHER_MAT[x % 4][y % 4]) {
		        discard;
		    }
		}

		float de_distanceVisibility(float cameraDistance, float ditherMinValue, float ditherNearDistance, float ditherFarDistance) {
		    float v = clamp(smoothstep(ditherNearDistance, ditherFarDistance, cameraDistance), ditherMinValue, 1.0);
		    return de_easeInOutCubic(v);
		}

		void de_applyDistanceDither(float cameraDistance, vec2 fragCoord, float ditherMinValue, float ditherPixelSize, float ditherNearDistance, float ditherFarDistance) {
		    de_applyDitherMatrix(de_distanceVisibility(cameraDistance, ditherMinValue, ditherNearDistance, ditherFarDistance), fragCoord, ditherPixelSize);
		}
		""";

	private static final String CALL_BLOCK =
		"\n\tde_applyDistanceDither(sphericalVertexDistance, gl_FragCoord.xy, DitherMinValue, DitherPixelSize, DitherNearDistance, DitherFarDistance);\n";

	public static String patchFragmentShader(String source) {
		if (source == null || source.contains(MARKER)) {
			return source;
		}

		Matcher main = MAIN_PATTERN.matcher(source);
		if (!main.find()) {
			return source;
		}

		String header = MARKER + " begin\n" + DITHER_BODY + MARKER + " end\n\n";

		return source.substring(0, main.start())
			+ header
			+ source.substring(main.start(), main.end())
			+ CALL_BLOCK
			+ source.substring(main.end());
	}
}
