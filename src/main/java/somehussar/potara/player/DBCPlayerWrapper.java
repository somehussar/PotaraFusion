package somehussar.potara.player;

import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreHDBC;
import somehussar.potara.PotaraConfig;
import somehussar.potara.PotaraMain;
import somehussar.potara.item.PotaraItemWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class DBCPlayerWrapper {

    EntityPlayer player;
    NBTTagCompound tag;

    private DBCPlayerWrapper(EntityPlayer p){
        this.player = p;
        this.tag = p.getEntityData().getCompoundTag("PlayerPersisted");
    }
    
    public static DBCPlayerWrapper getPlayer(EntityPlayer p){
        return new DBCPlayerWrapper(p);
    }

    public boolean canFuse(){
        return !JRMCoreH.isFused(player) && !hasNoFuse();
    }

    public boolean willingToFuse(){
        return JRMCoreH.PlyrSettingsB(this.player, 4);
    }

    public void fuseWith(EntityPlayer spectator){
        fuseWith(spectator, JRMCoreConfig.FznTime);
    }

    public void fuseWith(EntityPlayer spectator, int time){
        fuseWith(DBCPlayerWrapper.getPlayer(spectator), time);
    }
    public void fuseWith(DBCPlayerWrapper spectator, int time) {
        time *= 12;

        String p1Name = this.player.getCommandSenderName();
        String p2Name = spectator.player.getCommandSenderName();

        String fusionString = String.format("%s,%s,%d", p1Name, p2Name, time);

        this.tag.setString("jrmcFuzion", fusionString);
        spectator.tag.setString("jrmcFuzion", fusionString);

        this.setStatusEffect(10, true);
        spectator.setStatusEffect(11, true);

        this.removePlayerSetting(4);
        spectator.removePlayerSetting(4);

        this.setState2((byte) 0);
        spectator.setState2((byte) 0);

        String fusionName = JRMCoreHDBC.f_namgen(p1Name, p2Name);
        ChatComponentText fusionMessage = (ChatComponentText) new ChatComponentText(String.format("%s and %s became %s because of Potara Fusion!", p1Name, p2Name, fusionName)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
        this.player.addChatComponentMessage(fusionMessage);
        spectator.player.addChatComponentMessage(fusionMessage);
        PotaraMain.getLogger().info(String.format("%s and %s fused into %s!", p1Name, p2Name, fusionName));
        this.player.worldObj.playSoundAtEntity(this.player, "jinryuudragonbc:DBC.fusefin", 0.15f, 1.0f);

        int maxBody = this.getMaxStat(2);
        int maxKi = this.getMaxStat(5);

        spectator.setStatusEffect(3, false);
        spectator.setStatusEffect(4, false);
        spectator.setStatusEffect(5, false);

        this.tag.setInteger("jrmcBdy", maxBody);
        spectator.tag.setInteger("jrmcBdy", maxBody);
        this.tag.setInteger("jrmcEnrgy", maxKi);
        spectator.tag.setInteger("jrmcEnrgy", maxKi);
    }

    public boolean canUsePotara(){
        return canFuse() && getFusionLevel() == PotaraConfig.potaraFusionLevelRequirement;
    }

    public boolean hasPotaraInHand(){
        return PotaraItemWrapper.compare(player.getHeldItem());
    }

    public boolean hasNoFuse(){
        String fusion = tag.getString("jrmcFuzion");
        if (fusion == null){
            return false;
        }
        return !fusion.isEmpty() && !fusion.equals(" ") && !fusion.equals("0");
    }

    public EntityPlayer getPlayer() {
        return player;
    }
    public byte getPowerType(){
        return this.tag.getByte("jrmcPwrtyp");
    }
    public byte getRace(){
        return this.tag.getByte("jrmcRace");
    }

    public byte getClassId(){
        return this.tag.getByte("jrmcClass");
    }

    public int[] getPlayerAttributes(){
        return JRMCoreH.PlyrAttrbts(player);
    }

    /**
     * Bad reimplementation of a JRMC function (does not take in account skills that increase stats, ie. ki boost)
     *
     * thankfully it's not used here on any skill-boostable stats.
     * @param statId index of the stat you're interested in
     * @return max value of a stat
     */
    public int getMaxStat(int statId){
        return JRMCoreH.stat(player, statId, getPowerType(), statId, getPlayerAttributes()[statId], getRace(), getClassId(), 0.0f);
    }

    public void setState2(byte state){
        this.tag.setByte("jrmcState2", state);
    }

    public String getStatusEffects(){
        return this.tag.getString("jrmcStatusEff");
    }



    public boolean hasStatusEffect(int id){
        return getStatusEffects().contains(JRMCoreH.StusEfcts[id]);
    }

    public void setStatusEffectString(String stefs){
        this.tag.setString("jrmcStatusEff", stefs);
    }

    public void setStatusEffect(int id, boolean state){
        String se = JRMCoreH.StusEfcts[id];
        String statString = getStatusEffects();
        if(hasStatusEffect(id)){
            if(!state)
                setStatusEffectString(statString.replace(se, ""));
        }else{
            if(state)
                setStatusEffectString(statString+se);
        }
    }

    public void removePlayerSetting(int id){
        JRMCoreH.PlyrSettingsRem(player, id);
    }


    public boolean compare(EntityPlayer player){
        return this.player == player;
    }

    public boolean compare(DBCPlayerWrapper player){
        return this.compare(player.player);
    }

    public int getFusionLevel(){
        return JRMCoreH.SklLvl(0, player);
    }
}
