/*******************************************************************************
 * Copyright (c) 2014 Institute for Pervasive Computing, ETH Zurich and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Matthias Kovatsch - creator and main architect
 *    Stefan Jucker - DTLS implementation
 ******************************************************************************/
package org.eclipse.californium.scandium.dtls;


/**
 * This class represents a fragmented handshake message. It treats the
 * underlying handshake body as transparent data and just helps keeping track of
 * the fragment_offset and fragment_length.
 */
public class FragmentedHandshakeMessage extends HandshakeMessage {

	// Members ////////////////////////////////////////////////////////

	/** The fragmented handshake body. */
	private byte[] fragmentedBytes;

	/** The handshake message's type. */
	private HandshakeType type;

	/** The handshake message's unfragmented length. */
	private int messageLength;

	// Constructors ///////////////////////////////////////////////////

	/**
	 * Called when reassembling a handshake message or received a fragment
	 * during the handshake.
	 * 
	 * @param type
	 *            the message's type.
	 * @param messageLength
	 *            the message's total length.
	 * @param messageSeq
	 *            the message's message_seq.
	 * @param fragmentOffset
	 *            the message's fragment_offset.
	 * @param fragmentedBytes
	 *            the fragment's byte representation.
	 */
	public FragmentedHandshakeMessage(HandshakeType type, int messageLength, int messageSeq, int fragmentOffset, byte[] fragmentedBytes) {
		this.type = type;
		this.messageLength = messageLength;
		this.fragmentedBytes = fragmentedBytes;
		setMessageSeq(messageSeq);
		setFragmentOffset(fragmentOffset);
		setFragmentLength(fragmentedBytes.length);
	}

	/**
	 * Called when fragmenting a handshake message.
	 * 
	 * @param fragmentedBytes
	 *            the fragment's byte representation.
	 * @param type
	 *            the message's type.
	 * @param fragmentOffset
	 *            the fragment's fragment_offset.
	 * @param messageLength
	 *            the message's total (unfragmented) length.
	 */
	public FragmentedHandshakeMessage(byte[] fragmentedBytes, HandshakeType type, int fragmentOffset, int messageLength) {
		this.fragmentedBytes = fragmentedBytes;
		this.type = type;
		setFragmentOffset(fragmentOffset);
		setFragmentLength(fragmentedBytes.length);
		this.messageLength = messageLength;
	}

	// Methods ////////////////////////////////////////////////////////

	@Override
	public HandshakeType getMessageType() {
		return type;
	}

	@Override
	public int getMessageLength() {
		return messageLength;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("\t\t\tFragmented Handshake Message: " + fragmentedBytes.length + " bytes\n");
		sb.append("\t\t\t\t" + ByteArrayUtils.toHexString(fragmentedBytes) + "\n");

		return sb.toString();
	}

	// Serialization //////////////////////////////////////////////////

	@Override
	public byte[] fragmentToByteArray() {
		return fragmentedBytes;
	}

}