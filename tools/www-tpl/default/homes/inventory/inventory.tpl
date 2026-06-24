<div style="position: relative;">
<div id="webstore-categories-container">
	<h4>{{ locale.homes_inventory_categories }}</h4>
	<div id="webstore-categories">
<ul class="purchase-main-category">
		<li id="maincategory-1-stickers" class="selected-main-category webstore-selected-main">
			<div>{{ locale.homes_inventory_stickers }}</div>
			<ul class="purchase-subcategory-list" id="main-category-items-1">
				{% set num = 0 %}
				{% for category in stickerCategories %}
					{% if num == 0 %}<li id="subcategory-1-{{ category.getId() }}-stickers" class="subcategory-selected">{% else %}<li id="subcategory-1-{{ category.getId() }}-stickers" class="subcategory">{% endif %}
						<div>{{ category.getName() }}</div>
					</li>
				
				{% set num = num + 1 %}
				{% endfor %}

			</ul>
		</li>
		<li id="maincategory-4-backgrounds" class="main-category">
			<div>{{ locale.homes_inventory_backgrounds }}</div>
			<ul class="purchase-subcategory-list" id="main-category-items-4">
			
				{% for category in backgroundCategories %}
					<li id="subcategory-1-{{ category.getId() }}-stickers" class="subcategory">
						<div>{{ category.getName() }}</div>
					</li>
				{% endfor %}

			</ul>
		</li>
		<li id="maincategory-3-stickie_notes" class="main-category-no-subcategories">
			<div>{{ locale.homes_inventory_notes }}</div>
			<ul class="purchase-subcategory-list" id="main-category-items-3">

				<li id="subcategory-3-101-stickie_notes" class="subcategory">
					<div>29</div>
				</li>

			</ul>
		</li>
</ul>

	</div>
</div>

<div id="webstore-content-container">
	<div id="webstore-items-container">
		<h4>{{ locale.homes_inventory_select_an_item_by_clicking_it }}</h4>
		<div id="webstore-items"><ul id="webstore-item-list">
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
	<li class="webstore-item-empty"></li>
</ul></div>
	</div>
	<div id="webstore-preview-container">
		<div id="webstore-preview-default"></div>
		<div id="webstore-preview"></div>
	</div>
</div>

<div id="inventory-categories-container">
	<h4>{{ locale.homes_inventory_categories }}</h4>
	<div id="inventory-categories">
<ul class="purchase-main-category">
	<li id="inv-cat-stickers" class="selected-main-category-no-subcategories">
		<div>{{ locale.homes_inventory_stickers }}</div>
	</li>
	<li id="inv-cat-backgrounds" class="main-category-no-subcategories">
		<div>{{ locale.homes_inventory_backgrounds }}</div>
	</li>
	<li id="inv-cat-widgets" class="main-category-no-subcategories">
		<div>{{ locale.homes_inventory_widgets }}</div>
	</li>
	<li id="inv-cat-notes" class="main-category-no-subcategories">
		<div>{{ locale.homes_inventory_notes }}</div>
	</li>
</ul>

	</div>
</div>

<div id="inventory-content-container">
	<div id="inventory-items-container">
		<h4>{{ locale.homes_inventory_select_an_item_by_clicking_it }}</h4>
		<div id="inventory-items">
		
		{% if widgets|length == 0 %}
<div class="webstore-frank">
	<div class="blackbubble">
		<div class="blackbubble-body">

<p><b>{{ locale.homes_inventory_your_inventory_for_this_category_is_completely_empty }}</b></p>
<p>{{ locale.homes_inventory_to_be_able_to_purchase_stickers_backgrounds_and_notes_click_on_web_store_tab_and_select_a_category_and_a_product_then_click_purchase }}</p>

		<div class="clear"></div>
		</div>
	</div>
	<div class="blackbubble-bottom">
		<div class="blackbubble-bottom-body">
			<img src="{{ site.staticContentPath }}/web-gallery/images/box-scale/bubble_tail_small.gif" alt="" width="12" height="21" class="invitation-tail" />
		</div>
	</div>
	<div class="webstore-frank-image"><img src="{{ site.staticContentPath }}/web-gallery/images/frank/sorry.gif" alt="" width="57" height="88" /></div>
</div>
{% endif%}
		
		<ul id="inventory-item-list">

{% for widget in widgets %}
	<li id="inventory-item-{{ widget.getId() }}" title="{{ widget.getName() }}">
		<div class="webstore-item-preview {{ widget.getProduct().getCssClass() }}">
			<div class="webstore-item-mask">
			{% if widget.getAmount() > 1 %}
				<div class="webstore-item-count"><div>x{{ widget.getAmount() }}</div></div>	
			{% endif %}</div>
		</div>
	</li>
{% endfor %}

{% for box in emptyBoxes %}
	<li class="webstore-item-empty"></li>
{% endfor %}

</ul>
		</div>
	</div>
	
	<div id="inventory-preview-container">
		<div id="inventory-preview-default"></div>
		<div id="inventory-preview">
		<h4>&nbsp;</h4>

<div id="inventory-preview-box" {% if widgets|length == 0 %}style="display:none"{% endif %}></div>

		{% if widgets.length != 0 %}
<div id="inventory-preview-place" class="clearfix">
	<div class="clearfix">
		<a href="#" class="new-button" id="inventory-place"><b>{{ locale.homes_inventory_place }}</b><i></i></a>
	</div>
</div>
{% endif %}

</div>
	</div>
</div>

<div id="webstore-close-container">
	<div class="clearfix"><a href="#" id="webstore-close" class="new-button"><b>{{ locale.homes_inventory_close }}</b><i></i></a></div>
</div>
</div>