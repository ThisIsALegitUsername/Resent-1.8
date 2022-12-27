package net.minecraft.client.renderer.block.statemap;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public abstract class StateMapperBase implements IStateMapper {
	protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();

	public String getPropertyString(Map<IProperty, Comparable> parMap) {
		StringBuilder stringbuilder = new StringBuilder();

		for (Entry entry : parMap.entrySet()) {
			if (stringbuilder.length() != 0) {
				stringbuilder.append(",");
			}

			IProperty iproperty = (IProperty) entry.getKey();
			Comparable comparable = (Comparable) entry.getValue();
			stringbuilder.append(iproperty.getName());
			stringbuilder.append("=");
			stringbuilder.append(iproperty.getName(comparable));
		}

		if (stringbuilder.length() == 0) {
			stringbuilder.append("normal");
		}

		return stringbuilder.toString();
	}

	public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
		for (IBlockState iblockstate : blockIn.getBlockState().getValidStates()) {
			this.mapStateModelLocations.put(iblockstate, this.getModelResourceLocation(iblockstate));
		}

		return this.mapStateModelLocations;
	}

	protected abstract ModelResourceLocation getModelResourceLocation(IBlockState var1);
}