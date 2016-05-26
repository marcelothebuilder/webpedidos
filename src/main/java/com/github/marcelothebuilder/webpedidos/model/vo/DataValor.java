/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Marcelo Paixao Resende
 *
 */
public final class DataValor implements Serializable, Comparable<DataValor> {
	private static final long serialVersionUID = 1L;
	private final Date data;
	private final BigDecimal valor;

	public DataValor(Date data, BigDecimal valor) {
		super();
		this.data = data;
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "DataValor [" + (data != null ? "data=" + data + ", " : "") + (valor != null ? "valor=" + valor : "")
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DataValor))
			return false;
		DataValor other = (DataValor) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	/**
	 * Compara dois objetos DataValor, primeiro pela data e, caso esteja seja a
	 * mesma, compara pelo valor.
	 * 
	 * @param o
	 *            objeto a ser comparado.
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object.
	 */
	@Override
	public int compareTo(DataValor o) {
		int comparison = this.getData().compareTo(o.getData());
		if (comparison == 0) {
			comparison = o.getValor().compareTo(this.getValor());
		}
		return comparison;
	}

}
