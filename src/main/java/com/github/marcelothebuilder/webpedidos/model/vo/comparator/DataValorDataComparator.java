/**
 * 
 */
package com.github.marcelothebuilder.webpedidos.model.vo.comparator;

import java.util.Comparator;

import com.github.marcelothebuilder.webpedidos.model.vo.DataValor;

/**
 * @author Marcelo Paixao Resende
 *
 */
public class DataValorDataComparator implements Comparator<DataValor> {
	@Override
	public int compare(DataValor o1, DataValor o2) {
		return o1.getData().compareTo(o2.getData());
	}
}
