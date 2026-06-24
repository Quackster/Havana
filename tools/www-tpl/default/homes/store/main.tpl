<div style="position: relative;">
<div id="webstore-categories-container">
	<h4>{{ locale.homes_store_categories }}</h4>
	<div id="webstore-categories">
<ul class="purchase-main-category">
		<li id="maincategory-1-stickers" class="selected-main-category webstore-selected-main">
			<div>{{ locale.homes_store_stickers }}</div>
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
			<div>{{ locale.homes_store_backgrounds }}</div>
			<ul class="purchase-subcategory-list" id="main-category-items-4">
			
				{% for category in backgroundCategories %}
					<li id="subcategory-1-{{ category.getId() }}-stickers" class="subcategory">
						<div>{{ category.getName() }}</div>
					</li>
				{% endfor %}

			</ul>
		</li>
		<li id="maincategory-3-stickie_notes" class="main-category-no-subcategories">
			<div>{{ locale.homes_store_notes }}</div>
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
		<h4>{{ locale.homes_store_select_an_item_by_clicking_it }}</h4>
		<div id="webstore-items">
		<ul id="webstore-item-list">

{% for product in products %}
	<li id="webstore-item-{{ product.getId() }}" title="{{ product.getName() }}">
		<div class="webstore-item-preview {{ product.getCssClass() }}">
			<div class="webstore-item-mask">
			{% if product.getAmount() > 1 %}<div class="webstore-item-count"><div>x{{ product.getAmount() }}</div></div>{% endif %}
			</div>
		</div>
	</li>
{% endfor %}
	
{% for box in emptyBoxes %}
	<li class="webstore-item-empty"></li>
{% endfor %}


</ul>		</div>
	</div>
	<div id="webstore-preview-container">
		<div id="webstore-preview-default"></div>
		<div id="webstore-preview">
		
		<h4 title=""></h4>

<div id="webstore-preview-box"></div>

<div id="webstore-preview-price">
{{ locale.homes_store_price }}<br /><b>
	{% if product.getPrice() > 1 %}{{ product.getPrice() }} {{ locale.homes_store_credits }}{% else %}{{ product.getPrice() }} {{ locale.homes_store_credit }}{% endif %}
</b>
</div>

<div id="webstore-preview-purse">
{% if playerDetails.credits > 1 %}{{ locale.homes_store_you_have }}<br /><b>{{ playerDetails.credits }} {{ locale.homes_store_credits }}</b><br />{% else %}{{ locale.homes_store_you_have }}<br /><b>{{ playerDetails.credits }} {{ locale.homes_store_credit }}</b><br />{% endif %}
<a href="{{ site.sitePath }}/credits" target=_blank>{{ locale.homes_store_get_credits }}</a>
</div>

<div id="webstore-preview-purchase" class="clearfix">
	<div class="clearfix">
		<a href="#" class="new-button" id="webstore-purchase"><b>{{ locale.homes_store_purchase }}</b><i></i></a>
	</div>
</div>

<span id="webstore-preview-bg-text" style="display: none">{{ locale.homes_store_preview }}</span>		
</div>
	</div>
</div>

<div id="inventory-categories-container">
	<h4>{{ locale.homes_store_categories }}</h4>
	<div id="inventory-categories">
<ul class="purchase-main-category">
	<li id="inv-cat-stickers" class="selected-main-category-no-subcategories">
		<div>{{ locale.homes_store_stickers }}</div>
	</li>
	<li id="inv-cat-backgrounds" class="main-category-no-subcategories">
		<div>{{ locale.homes_store_backgrounds }}</div>
	</li>
	<li id="inv-cat-widgets" class="main-category-no-subcategories">
		<div>{{ locale.homes_store_widgets }}</div>
	</li>
	<li id="inv-cat-notes" class="main-category-no-subcategories">
		<div>{{ locale.homes_store_notes }}</div>
	</li>
</ul>

	</div>
</div>

<div id="inventory-content-container">
	<div id="inventory-items-container">
		<h4>{{ locale.homes_store_select_an_item_by_clicking_it }}</h4>
		<div id="inventory-items"><ul id="inventory-item-list">
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
	<div id="inventory-preview-container">
		<div id="inventory-preview-default"></div>
		<div id="inventory-preview"><h4>&nbsp;</h4>

<div id="inventory-preview-box"></div>

<div id="inventory-preview-place" class="clearfix">
	<div class="clearfix">
		<a href="#" class="new-button" id="inventory-place"><b></b><i></i></a>
	</div>
</div>

</div>
	</div>
</div>

<div id="webstore-close-container">
	<div class="clearfix"><a href="#" id="webstore-close" class="new-button"><b>{{ locale.homes_store_close }}</b><i></i></a></div>
</div>
</div>
