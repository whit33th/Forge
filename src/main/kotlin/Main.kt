abstract class Item(
    val name: String,
    private val description: String,
    var durability: Int
) {
    open val type: String? = null
    open val armor: Int? = null
    open val rare: String? = null
    open val effect: String? = null
    open val activeEffectName: String? = null
    open val damage: Int? = null
    open val activeEffect: String? = null

    abstract fun use()

    open fun showItemDescription() {
        name.let { println("Item: $it") }
        type?.let { println("Type: $it") }
        rare?.let { println("Rare: $it") }
        damage?.let { println("Damage: $it") }
        armor?.let { println("Armor: $it") }
        durability.let { println("Durability: $it") }
        effect?.let { println("Effect: $it") }
        activeEffectName?.let { println("Active Effect Name: $it") }
        activeEffect?.let { println("Active Effect: $it") }
        description.let { println("***\nDescription: $it ") }
    }
}

class Inventory {
    internal val items = mutableListOf<Item>()

    fun addItem(item: Item) {
        items.add(item)
    }

    fun removeItem(item: Item) {
        items.remove(item)
    }

    fun displayInventory() {
        for ((index, item) in items.withIndex()) {
            println("$index. ${item.name} (Durability: ${item.durability})")
        }
    }

    fun getItem(index: Int): Item? {
        return items.getOrNull(index)
    }

    fun showItemDescription(index: Int) {
        val item = getItem(index)
        if (item != null) {
            item.showItemDescription()
        } else {
            println("Invalid item number.")
        }
    }
}

class Artifacts(
    name: String,
    override val rare: String,
    override val effect: String?,
    override val activeEffectName: String?,
    override val activeEffect: String?,
    description: String,
    durability: Int
) : Item(name, description, durability) {

    override fun use() {
        if (durability > 0) {
            println("-----------------------------------------------")
            println("Used artifact: $name (Durability: $durability)")
            durability--
        } else {
            println("-----------------------------------------------")
            println("Artifact $name is damaged.")
        }
    }
}

class Armor(
    name: String,
    override val type: String,
    override val rare: String,
    armor: Int,
    override val effect: String?,
    override val activeEffectName: String?,
    override val activeEffect: String?,
    description: String,
    durability: Int
) : Item(name, description, durability) {
    override val armor = armor

    override fun use() {
        if (durability > 0) {
            println("-----------------------------------------------")
            println("Used armor: $name (Durability: $durability)")
            durability--
        } else {
            println("-----------------------------------------------")
            println("Armor $name is damaged.")
        }
    }
}

class Weapon(
    name: String,
    override val type: String,
    override val rare: String,
    override val damage: Int,
    override val effect: String?,
    override val activeEffectName: String?,
    activeEffect: String?,
    description: String,
    durability: Int
) : Item(name, description, durability) {
    override val activeEffect = activeEffect

    override fun use() {
        if (durability > 0) {
            println("-----------------------------------------------")
            println("Used weapon: $name (Durability: $durability)")
            durability--
        } else {
            println("-----------------------------------------------")
            println("Weapon $name is damaged.")
        }
    }
}
fun checkName() {
    println("Welcome, weary traveler! State thy name.")
    val name = readln()
    println("How may I assist you $name?")
}

fun main() {
    val inventory = Inventory()

    val forgeItemList = mapOf<String, (Int) -> Item>(
        "1" to { Weapon(
            "Blade of kingslayer", "Melee", "Immortal",
            73,
            "Pierces all armor",
            null,
            null,
            "The Blade of Kingslayer is a weapon shrouded in infamy, forever tied to treacherous regicide and the fall of a mighty monarch.\nForged with malevolent intent, this dark blade embodies the weight of a kingdom's fate.\n- Are you willing to sacrifice your honor?", 10) },
        "2" to { Weapon(
            "Halberd of the Mist House",
            "Melee",
            "Legendary",
            91,
            "The blade is enshrouded in mystical fog, dealing additional ethereal damage to foes.",
            "Whispering Shadows",
            "When invoked, it conjures the ancient, spectral shades of the mist that move exactly like their creator to enshroud the wielder, veiling them in invisibility",
            "Passed down through the enigmatic lineage of fog dwellers.\nIt embodies the secrets of the Fog House, granting the wielder unparalleled elusiveness in battle", 10) },
        "3" to { Weapon(
            "A Fan Dagger",
            "Melee",
            "Rare",
            39,
            "Poison (9 seconds): deals 4 damage every 3 seconds. ",
            null,
            null,
            "A seemingly delicate fan, this dagger is a silent and swift killer.\nIt carries venom, making it ideal for assassins.", 10) },

        "5" to { Weapon (
            "Hunting bow", "Range", "Common",
            45,
            null,
            null,
            null,
            "The hunting bow is a reliable and versatile weapon favoured by hunters and adventurers.", 99)},
        "6" to { Weapon (
            "Wooden Sword", "Melee", "Common",
            12,
            null,
            null,
            null,
            "The Wooden Sword is a humble yet essential training and practice weapon. - Its a good place to start.", 99)},

        "7" to { Armor(
            "Carian Enlightened Armor",
            "Heavy Armor",
            "Legendary",
            100,
            "Sentinel's Resilience: These armors fortify the wearer's defenses, reducing damage and enhancing resistance to both physical and magical attacks.",
            null,
            null,
            "The Enlightened Circle Armor is a legendary set, rumored to be forged by the divine themselves.\nIt exudes an aura of impenetrable protection, guarding its wearer against the most dire of threats.", 10) },
        "8" to { Armor(
            "Shinobi's Shadow Garb",
            "Light Armor",
            "Rare",
            45,
            "Shinobi Swiftness: This armor set is designed for swift and silent movement, increasing agility and decreasing the sound of the wearer's footsteps.",
            null,
            null,
            "The Shinobi's Shadow Garb is a set of armor favored by agile assassins and shinobi.\nIts design prioritizes stealth and agility, allowing for swift and silent movements in the shadows.", 10
        ) },
        "9" to { Artifacts(
            "Broken teapot",
            "Rare",
            "+15% Magic resistance.",
            null,
            null,
            "Wisps of steam raised up from the teapot, but it is now broken. It radiates a faint sense of loss", 1
        ) },
        "10" to { Artifacts(
            "Bloody Sacrifice Amulet",
            "Immortal",
            "+10% HP",
            "Rivers of blood",
            "Cast a charge of bloody waves on the enemy, but with each use, the wearer must sacrifice a portion *(20% HP)* of their health.",
            "This amulet is covered in the blood of sacrifices and is associated with\nancient rituals and cults. Its wearer can offer sacrifices for magical power.", 99
        ) },
        "11" to { Artifacts(
            "Wraith drums",
            "Rare",
            "+7% Movement speed",
            "Ghost form",
            "You enter ghost form for 4 seconds",
            "From these drums come the echoes of the dead. \nI wonder what would happen if you knocked on them?", 3
        ) }
    )
    checkName()

    while (true) {
        println("-----------------------------------------------")
        println("Choose an option:")
        println("1. Forge an item")
        println("2. Use an item")
        println("3. Display inventory")
        println("4. Leave ")
        println("5. Rest at bonfire")

        val choice = readlnOrNull() ?: continue

        when (choice) {
            "1" -> {
                println("-----------------------------------------------")
                println("Available items for forging:")
                forgeItemList.forEach { (number, item) ->
                    println("$number. ${item(10).name}")
                }

                println("-----------------------------------------------")
                print("Choose an item to forge (enter the number): ")
                val number = readlnOrNull() ?: continue

                val selectedItem = forgeItemList[number]
                if (selectedItem != null) {
                    val newItem = selectedItem(10)
                    inventory.addItem(newItem)
                    println("----------------------------------------------------")
                    println("You forged a new item: ${newItem.name}")
                } else {
                    println("Invalid item number.")
                    println("-----------------------------------------------")
                }
            }
            "2" -> {
                if (inventory.items.isNotEmpty()) {
                    println("-----------------------------------------------")
                    inventory.displayInventory()
                    println("-----------------------------------------------")
                    print("Choose an item to use (enter the number): ")

                    val number = readlnOrNull() ?: continue
                    val selectedNumber = number.toIntOrNull()
                    if (selectedNumber != null && selectedNumber >= 0 && selectedNumber < inventory.items.size) {
                        val item = inventory.getItem(selectedNumber)
                        if (item != null) {
                            item.use()
                        } else {
                            println("*Invalid item number*")
                            println("-----------------------------------------------")
                        }
                    }
                } else {
                    println("*Inventory is empty*")
                }
            }

            "3" -> {
                if (inventory.items.isNotEmpty()) {
                    while (true) {
                        println("Inventory:\n-----------------------------------------------")
                        inventory.displayInventory()
                        println("-----------------------------------------------")
                        println("Enter the item number to see its description")
                        println("'r' to remove item")
                        println("'x' to return to the function selection")

                        val option = readlnOrNull() ?: continue

                        if (option == "x") {
                            break
                        }
                        if (option == "r") {
                            println("Enter the index of the item you want to remove:")
                            val deleteIndex = readlnOrNull()?.toInt()

                            if (deleteIndex != null && deleteIndex >= 0 && deleteIndex < inventory.items.size) {
                                inventory.removeItem(inventory.items[deleteIndex])
                                println("Item at index $deleteIndex removed from inventory.")
                            } else {
                                println("*Invalid index. Item removal failed*")
                            }
                            break
                        }

                        val selectedNumber = option.toIntOrNull()
                        if (selectedNumber != null && selectedNumber >= 0 && selectedNumber < inventory.items.size) {
                            println("\n-----------------------------------------------")
                            inventory.showItemDescription(selectedNumber)
                            println("-----------------------------------------------\n")
                        } else {
                            println("-----------------------------------------------")
                            println("*Invalid item number*")
                            println("-----------------------------------------------")
                        }
                    }
                } else {
                    println("*Inventory is empty*")
                }
            }
            "4" -> {
                println("-----------------------------------------------")
                println("Go, before the abyss consumes us both!")
                break
            }
            "5" -> {
                println("*The fire crackles, and the stars twinkle brightly in the night sky*")
                val option = readlnOrNull()

            }
            else -> {
                println("Invalid option. Choose again.")
            }
        }
    }
}
