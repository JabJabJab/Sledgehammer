package sledgehammer.util;

/**
 * Enumeration for ProjectZomboid's Lua Event types.
 * 
 * @author Jab
 */
public enum LuaEvent {
	// @formatter:off
	OnGameBoot, 
	OnPreGameStart,
	OnTick,
	OnTickEvenPaused,
	OnFETick,
	OnGameStart,
	OnCharacterCollide,
	OnObjectCollide,
	OnPlayerUpdate,
	OnZombieUpdate,
	OnTriggerNPCEvent,
	OnMultiTriggerNPCEvent,
	OnLoadMapZones,
	OnAddBuilding,
	OnCreateLivingCharacter,
	OnChallengeQuery,
	OnFillInventoryObjectContextMenu,
	OnPreFillInventoryObjectContextMenu,
	OnFillWorldObjectContextMenu,
	OnPreFillWorldObjectContextMenu,
	OnMakeItem,
	OnWeaponHitCharacter,
	OnWeaponSwing,
	OnWeaponHitTree,
	OnWeaponSwingHitPoint,
	OnPlayerCancelTimedAction,
	OnLoginState,
	OnLoginStateSuccess,
	OnCharacterCreateStats,
	OnLoadSoundBanks,
	OnDoTileBuilding,
	OnDoTileBuilding2,
	OnDoTileBuilding3,
	OnConnectFailed,
	OnConnected,
	OnDisconnect,
	OnConnectionStateChanged,
	OnScoreboardUpdate,
	OnNewSurvivorGroup,
	OnPlayerSetSafehouse,
	OnLoad,
	AddXP,
	LevelPerk,
	OnSave,
	OnMainMenuEnter,
	OnPreMapLoad,
	OnMapLoadCreateIsoObject,
	OnCreateSurvivor,
	OnCreatePlayer,
	OnPlayerDeath,
	OnZombieDead,
	OnCharacterMeet,
	OnSpawnRegionsLoaded,
	OnPostMapLoad,
	OnAIStateExecute,
	OnAIStateEnter,
	OnAIStateExit,
	OnAIStateChange,
	OnPlayerMove,
	OnInitWorld,
	OnNewGame,
	OnIsoThumpableLoad,
	OnIsoThumpableSave,
	ReuseGridsquare,
	LoadGridsquare,
	EveryTenMinutes,
	EveryDays,
	EveryHours,
	OnDusk,
	OnDawn,
	OnEquipPrimary,
	OnEquipSecondary,
	OnClothingUpdated,
	OnRainStart,
	OnRainStop,
	OnAmbientSound,
	OnResetLua,
	OnSeeNewRoom,
	OnNewFire,
	OnFillContainer,
	OnChangeWeather,
	OnDestroyIsoThumpable,
	OnPostSave,
	OnWaterAmountChange,
	OnClientCommand,
	OnContainerUpdate,
	OnObjectAdded,
	onLoadModDataFromServer,
	OnGameTimeLoaded,
	OnWorldMessage,
	SendCustomModData,
	ServerPinged,
	OnServerStarted,
	OnLoadedTileDefinitions,
	DoSpecialTooltip,
	OnCoopJoinFailed,
	OnDeviceText,
	OnRadioInteraction,
	OnAcceptInvite,
	OnCoopServerMessage;
	// @formatter:on
}