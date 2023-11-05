import kotlin.random.Random

abstract class Item(
    val name: String,
    val description: String,
    var durability: Int
)

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
            println("Item Description '${item.name}':\n${item.description}")
        } else {
            println("Invalid item number.")
        }
    }
}

class Weapon(
    name: String,
    description: String,
    durability: Int
) : Item(name, description, durability) {
    fun use() {
        if (durability > 0) {
            println("-----------------------------------------------")
            println("Used item: $name (Durability: $durability)")
            durability--
        } else {
            println("-----------------------------------------------")
            println("Item $name is damaged.")
        }
    }
}

fun main() {
    val inventory = Inventory()

    val forgeItemList = mapOf<String, (Int, Inventory) -> Item>(
        "1" to { durability, inventory -> Weapon("Shovel", "For digging", durability) },
        "2" to { durability, inventory -> Weapon("Axe", "For chopping", durability) },
        "3" to { durability, inventory -> Weapon("Sword", "For combat", durability) }
    )

    while (true) {
        println("-----------------------------------------------")
        println("Choose an option:")
        println("1. Forge an item")
        println("2. Use an item")
        println("3. Display inventory")
        println("4. Quit")

        val choice = readLine() ?: continue

        when (choice) {
            "1" -> {
                println("-----------------------------------------------")
                println("Available items for forging:")
                forgeItemList.forEach { (number, item) ->
                    println("$number. ${item(10, inventory).name}")
                }

                println("-----------------------------------------------")
                print("Choose an item to forge (enter the number): ")
                val number = readLine() ?: continue
                val selectedItem = forgeItemList[number]
                if (selectedItem != null) {
                    val newItem = selectedItem(10, inventory)
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

                    val number = readLine() ?: continue
                    val selectedNumber = number.toIntOrNull()
                    if (selectedNumber != null && selectedNumber >= 0 && selectedNumber < inventory.items.size) {
                        val item = inventory.getItem(selectedNumber)
                        if (item != null) {
                            if (item is Weapon) {
                                item.use()
                            }
                        } else {
                            println("Invalid item number.")
                            println("-----------------------------------------------")
                        }
                    }
                } else {
                    println("Invalid item number.")
                }

            }

            "3" -> {
                println("-----------------------------------------------")
                inventory.displayInventory()
                println("-----------------------------------------------")
                if (inventory.items.isNotEmpty()) {
                    while (true) {
                        println("Enter the item number to see its description")
                        println("Enter 'x' to return to the function selection")

                        val option = readLine() ?: continue

                        if (option == "x") {
                            break
                        }

                        val selectedNumber = option.toIntOrNull()
                        if (selectedNumber != null && selectedNumber >= 0 && selectedNumber < inventory.items.size) {
                            println("***********************************************")
                            inventory.showItemDescription(selectedNumber)
                            println("***********************************************")
                        } else {
                            println("Invalid item number.")
                            println("-----------------------------------------------")
                        }
                    }
                } else {
                    println("*Inventory is empty*")
                }
            }
            "4" -> {
                println("-----------------------------------------------")
                println("Goodbye!")
                break
            }
            else -> {
                println("Invalid option. Choose again.")
            }
        }
    }
}
