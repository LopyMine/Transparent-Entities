package net.lopymine.ed.dithering.vanilla;

import com.mojang.blaze3d.buffers.*;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.lopymine.ed.config.distance.DitheringDataConfig;
import org.lwjgl.system.MemoryStack;

public class VanillaDitheringDataBuffer {

	public static final int SIZE = new Std140SizeCalculator()
			.putFloat()
			.putFloat()
			.putFloat()
			.putFloat()
			.get();

	public static final GpuBuffer BUFFER = RenderSystem.getDevice().createBuffer(() -> "VanillaDitheringDataBuffer UBO", 136, SIZE);

	public static void update() {
		EntityDitheringConfig config = EntityDitheringConfig.getInstance();
		DitheringDataConfig distance = config.getDitheringDataConfig();

		try (MemoryStack memoryStack = MemoryStack.stackPush()) {
			ByteBuffer byteBuffer = Std140Builder.onStack(memoryStack, SIZE)
					.putFloat((float) distance.getMinValue())
					.putFloat((float) distance.getPixelSize())
					.putFloat((float) distance.getNear())
					.putFloat((float) distance.getFar())
					.get();
			RenderSystem.getDevice().createCommandEncoder().writeToBuffer(BUFFER.slice(), byteBuffer);
		}
	}

}