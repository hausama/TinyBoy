package tinyboy.core;

import java.util.Arrays;

import javr.util.BitList;

/**
 * Represents a sequence of input values for the TinyBoy. The input sequence is
 * split into a series of "pulses" of a given (fixed) length. Each pulse is
 * either one of the buttons being pressed or nothing. For example, the sequence
 * <code>"LLRR_"</code> consists of two pulses with the left button down, two
 * with the right button down and one with nothing down.
 *
 * @author David J. Pearce
 *
 */
public class TinyBoyInputSequence implements BitList {
	/**
	 * The number of inputs is determined by the number of buttons on the control
	 * pad.
	 */
	private final int NUM_INPUTS = ControlPad.Button.values().length;
	/**
	 * The sequence of button push pulses.
	 */
	private final ControlPad.Button[] pulses;
	/**
	 * The width of a single pulse.
	 */
	private final int width;
	/**
	 * The number of pulse in the sequence.
	 */
	private final int size;

	/**
	 * Create a new input sequence with a give number of pulses (i.e. button pushes)
	 * where each pulse has a given width (in cycles).
	 *
	 * @param size
	 *            The number of pulses in this sequence.
	 * @param width
	 *            The width of a pulse in this sequence.
	 */
	public TinyBoyInputSequence(int size, int width) {
		this.pulses = new ControlPad.Button[size];
		this.width = width;
		this.size = size;
	}

	/**
	 * Create a copy of a given input sequence.
	 *
	 * @param list
	 */
	public TinyBoyInputSequence(TinyBoyInputSequence list) {
		this.width = list.width;
		this.size = list.size;
		this.pulses = Arrays.copyOf(list.pulses, size);
	}

	@Override
	public int size() {
		return (size * width) * NUM_INPUTS;
	}

	@Override
	public boolean get(int ith) {
		int index = ith / (NUM_INPUTS * width);
		int input = ith % NUM_INPUTS;
		ControlPad.Button pulse = pulses[index];
		boolean r = pulse != null && pulse.ordinal() == input;
		return r;
	}

	@Override
	public void set(int ith, boolean value) {
		// Not supported!
		throw new UnsupportedOperationException();
	}

	/**
	 * Set a specific pulse to be either a button push, or no button pushed
	 * (<code>null</code>).
	 *
	 * @param pulse
	 * @param button
	 */
	public void setPulse(int pulse, ControlPad.Button button) {
		pulses[pulse] = button;
	}

	@Override
	public String toString() {
		String r = "";
		for (int i = 0; i != pulses.length; ++i) {
			ControlPad.Button pulse = pulses[i];
			if(pulse == null) {
				r = r + "_";
			} else {
				r += pulse.toString().charAt(0);
			}
		}
		return r;
	}
}