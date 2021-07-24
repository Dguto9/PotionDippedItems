package com.potionenchants.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings("deprecation")
public class EventListener implements Listener {
	
    private ItemStack potion;
    private ItemStack object;
  //private ItemStack ingot;
    private boolean recipeCorrect;
    private String loreString;
    private ItemStack air = new ItemStack(Material.AIR);
    
    boolean debugMode = false;

	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (debugMode) {
			event.getEntity().sendMessage("EntityDamagedByEntity was run...");
			event.getDamager().sendMessage("EntityDamagedByEntity was run...");
		}
		if (event.getEntity() instanceof LivingEntity) {
			
		}
		else {
			if (debugMode) {
				event.getDamager().sendMessage("Entity was not living");
			}
			return;
		}
		
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			if (damager.hasPermission("PotionDipped.Weapon.PotionType.Use")) {
				ItemStack weapon = damager.getInventory().getItemInMainHand();
				NamespacedKey key = new NamespacedKey(Main.getInstance(), "potioneffect");
				ItemMeta weaponMeta = weapon.getItemMeta();
				CustomItemTagContainer tagContainer = weaponMeta.getCustomTagContainer();
				if(!tagContainer.hasCustomTag(key, ItemTagType.STRING)) {
					if (debugMode) {
						event.getEntity().sendMessage("Item did not have tag!");
						event.getDamager().sendMessage("Item did not have tag!");
					}
					return;
				}
				else {
					LivingEntity target = (LivingEntity) event.getEntity();
					target.addPotionEffect(PotionEffectType.getByName(tagContainer.getCustomTag(key, ItemTagType.STRING)).createEffect(60, 1));
					if (debugMode) {
						event.getEntity().sendMessage("Added effect!");
						event.getDamager().sendMessage("Added effect!");
					}		
				}
			}
		}
		else {
			if (debugMode) {
				event.getEntity().sendMessage("Damager was not player");
			}
		}
		if (event.getEntity() instanceof Player) {
			Player entity = (Player) event.getEntity();
			if (entity.hasPermission("PotionDipped.Armor.PotionType.Use")) {
				ItemStack chestplate = entity.getInventory().getChestplate();
				ItemStack leggings = entity.getInventory().getLeggings();
				NamespacedKey key = new NamespacedKey(Main.getInstance(), "potioneffect");
				ItemMeta chestplateMeta = chestplate.getItemMeta();
				ItemMeta leggingsMeta = leggings.getItemMeta();
				CustomItemTagContainer chestTagContainer = chestplateMeta.getCustomTagContainer();
				CustomItemTagContainer legsTagContainer = leggingsMeta.getCustomTagContainer();
				if(chestTagContainer.hasCustomTag(key, ItemTagType.STRING)) {
					entity.addPotionEffect(PotionEffectType.getByName(chestTagContainer.getCustomTag(key, ItemTagType.STRING)).createEffect(60, 1));
					if (debugMode) {
						event.getEntity().sendMessage("Added chest effect!");
						event.getDamager().sendMessage("Added chest effect!");
					}
				}
				else {
					if (debugMode) {
						event.getEntity().sendMessage("Chestplate did not have tag!");
						event.getDamager().sendMessage("Chestplate did not have tag!");
					}
					return;
				}
				if(legsTagContainer.hasCustomTag(key, ItemTagType.STRING)) {
					entity.addPotionEffect(PotionEffectType.getByName(legsTagContainer.getCustomTag(key, ItemTagType.STRING)).createEffect(60, 1));
					if (debugMode) {
						event.getEntity().sendMessage("Added effect!");
						event.getDamager().sendMessage("Added effect!");
					}
				}
				else {
					if (debugMode) {
						event.getEntity().sendMessage("Leggings did not have tag!");
						event.getDamager().sendMessage("Leggings did not have tag!");
					}
					return;
				}
			}
		}
		else {
			if (debugMode) {
				event.getDamager().sendMessage("Entity was not player");
			}
		}
	}
	
	@EventHandler
	public void anvilLis(PrepareAnvilEvent e) {
		ItemStack[] slotAnvil = e.getInventory().getContents();
		List<HumanEntity> viewers = e.getInventory().getViewers();
		if(slotAnvil[0] == null || slotAnvil[1] == null) {
			recipeCorrect = false;
			return;
		}
        if (slotAnvil[0].getType() != Material.LINGERING_POTION && slotAnvil[1].getType() != Material.LINGERING_POTION) {
        	recipeCorrect = false;
        	return;
        }
        if ((slotAnvil[0].getType() != Material.NETHERITE_INGOT && slotAnvil[1].getType() != Material.NETHERITE_INGOT) && (slotAnvil[0].getType() != Material.DIAMOND_SWORD && slotAnvil[1].getType() != Material.DIAMOND_SWORD) && (slotAnvil[0].getType() != Material.NETHERITE_SWORD && slotAnvil[1].getType() != Material.NETHERITE_SWORD) && (slotAnvil[0].getType() != Material.DIAMOND_CHESTPLATE && slotAnvil[1].getType() != Material.DIAMOND_CHESTPLATE) && (slotAnvil[0].getType() != Material.NETHERITE_CHESTPLATE && slotAnvil[1].getType() != Material.NETHERITE_CHESTPLATE) && (slotAnvil[0].getType() != Material.DIAMOND_LEGGINGS && slotAnvil[1].getType() != Material.DIAMOND_LEGGINGS) && (slotAnvil[0].getType() != Material.NETHERITE_LEGGINGS && slotAnvil[1].getType() != Material.NETHERITE_LEGGINGS)) {
        	recipeCorrect = false;
        	return;
        }
        if (slotAnvil[0].getType() == Material.LINGERING_POTION) {
        	potion = new ItemStack(slotAnvil[0]);	
        }
        else if (slotAnvil[1].getType() == Material.LINGERING_POTION){
        	potion = new ItemStack(slotAnvil[1]);
        }
        if (slotAnvil[0].getType() == Material.DIAMOND_SWORD || slotAnvil[0].getType() == Material.NETHERITE_SWORD || slotAnvil[0].getType() == Material.DIAMOND_CHESTPLATE || slotAnvil[0].getType() == Material.NETHERITE_CHESTPLATE || slotAnvil[0].getType() == Material.DIAMOND_LEGGINGS || slotAnvil[0].getType() == Material.NETHERITE_LEGGINGS) {
        	object = new ItemStack(slotAnvil[0]); 
        } 
        else if (slotAnvil[1].getType() == Material.DIAMOND_SWORD || slotAnvil[1].getType() == Material.NETHERITE_SWORD || slotAnvil[1].getType() == Material.DIAMOND_CHESTPLATE || slotAnvil[1].getType() == Material.NETHERITE_CHESTPLATE || slotAnvil[1].getType() == Material.DIAMOND_LEGGINGS || slotAnvil[1].getType() == Material.NETHERITE_LEGGINGS) {
        	object = new ItemStack(slotAnvil[1]);
        }
        if (object.getType() == Material.DIAMOND_SWORD || object.getType() == Material.NETHERITE_SWORD) {
        	if (!viewers.get(0).hasPermission("PotionDipped.Weapon.PotionType.Craft")) {
        		viewers.get(0).sendMessage("You do not have permission to create potion dipped weapons!");
        		return;
        	}
        }
        if (object.getType() == Material.DIAMOND_CHESTPLATE || object.getType() == Material.NETHERITE_CHESTPLATE || object.getType() == Material.DIAMOND_LEGGINGS || object.getType() == Material.NETHERITE_LEGGINGS) {
        	if (!viewers.get(0).hasPermission("PotionDipped.Armor.PotionType.Craft")) {
        		viewers.get(0).sendMessage("You do not have permission to create potion dipped armor!");
        		return;
        	}
        }
       /* if (slotAnvil[0].getType() == Material.NETHERITE_INGOT)
        	ingot = new ItemStack(slotAnvil[0]);
        else if (slotAnvil[1].getType() == Material.NETHERITE_INGOT) {
        	ingot = new ItemStack(slotAnvil[1]);
        }*/
        recipeCorrect = true;
        if (object == null) {
        	loreString = "Shimmering";
        	e.setResult(setItemLore(potion, loreString));
        }
        else {
        	NamespacedKey key = new NamespacedKey(Main.getInstance(), "potioneffect");
        	PotionMeta meta = (PotionMeta) potion.getItemMeta();
        	ItemMeta itemMeta = (ItemMeta) potion.getItemMeta();
        	PotionData data = meta.getBasePotionData();
        	loreString = data.getType().getEffectType().getName();
        	CustomItemTagContainer tagContainer = itemMeta.getCustomTagContainer();
    		if(!tagContainer.hasCustomTag(key, ItemTagType.STRING)) {
    			recipeCorrect = false;
    			object = null;
    			potion = null;
    			return;
    		}
        	e.setResult(setItemLore(object, loreString));
        }
	} 
	
	@EventHandler
    public void anvil(InventoryClickEvent e){
        if(!(e.getInventory() instanceof AnvilInventory)){
            return;
        }
       
        if(e.getSlotType() != InventoryType.SlotType.RESULT){
            return;
        }
      
       
        if(e.getInventory().getItem(1) == null || e.getInventory().getItem(0) == null){
            return;
        }
        if(!recipeCorrect) {
    	    return;
        }
        ItemStack slot1 = e.getInventory().getItem(0);
        ItemStack slot2 = e.getInventory().getItem(1);
        ItemStack slot3 = e.getInventory().getItem(2);
        int slot1Amount = slot1.getAmount();
        int slot2Amount = slot2.getAmount();
        slot1.setAmount(slot1Amount-1);
        slot2.setAmount(slot2Amount-1);
        e.getInventory().setItem(2, air);
        HumanEntity player = e.getWhoClicked();
        player.setItemOnCursor(slot3);
    }
	
	public ItemStack setItemLore(ItemStack result, String lore) {	
        ItemMeta meta = result.getItemMeta();
        ArrayList<String> resultLores = new ArrayList<String>();
        resultLores.add(lore);
        meta.setLore(resultLores);
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "potioneffect");
        meta.getCustomTagContainer().setCustomTag(key, ItemTagType.STRING, lore);
		result.setItemMeta(meta);
        object = null;
        potion = null;
		return result;
	}
} 